package com.example.digibuddy.features.log

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.digibuddy.data.model.User
import com.example.digibuddy.di.ViewModelFactory
import com.example.digibuddy.features.recommendations.RecommendationEngine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputLogScreen(navController: NavController, userProfile: User) {
    val context = LocalContext.current
    // ViewModel to handle fetching predictions and saving logs
    val logViewModel: LogViewModel = viewModel(factory = ViewModelFactory(context))
    val predictionResult by logViewModel.predictionResult.collectAsState()

    // State for log-specific inputs
    var deviceHours by remember { mutableStateOf(0f) }
    var phoneUnlocks by remember { mutableStateOf("") }
    var notificationsPerDay by remember { mutableStateOf("") }
    var socialMediaMinutes by remember { mutableStateOf("") }
    var studyMinutes by remember { mutableStateOf("") }
    var physicalActivityDays by remember { mutableStateOf(0f) }
    var sleepHours by remember { mutableStateOf(0f) }
    var sleepQuality by remember { mutableStateOf(0f) }

    // State to control the confirmation dialog
    var showConfirmationDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Log Your Daily Habits", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // --- Input fields for habits (Sliders, Dropdowns, TextFields) ---
        DropdownInput(label = "Phone Unlocks per Day", options = listOf("10", "20", "50", "100", "200"), selectedOption = phoneUnlocks, onOptionSelected = { phoneUnlocks = it })
        DropdownInput(label = "Notifications per Day", options = listOf("10", "25", "50", "100", "250"), selectedOption = notificationsPerDay, onOptionSelected = { notificationsPerDay = it })

        TextField(value = socialMediaMinutes, onValueChange = { socialMediaMinutes = it }, label = { Text("Social Media Minutes per Day") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        TextField(value = studyMinutes, onValueChange = { studyMinutes = it }, label = { Text("Study Minutes per Day") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

        Text("Device Hours per Day: ${deviceHours.toInt()}")
        Slider(value = deviceHours, onValueChange = { deviceHours = it }, valueRange = 0f..24f)

        Text("Physical Activity Days per Week: ${physicalActivityDays.toInt()}")
        Slider(value = physicalActivityDays, onValueChange = { physicalActivityDays = it }, valueRange = 0f..7f)

        Text("Sleep Hours per Night: ${sleepHours.toInt()}")
        Slider(value = sleepHours, onValueChange = { sleepHours = it }, valueRange = 0f..12f)

        Text("Sleep Quality (1-5): ${sleepQuality.toInt()}")
        Slider(value = sleepQuality, onValueChange = { sleepQuality = it }, valueRange = 1f..5f)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Combine profile data and new log data to get a prediction
                logViewModel.getStressPrediction(
                    userProfile = userProfile, // Pass the whole profile
                    deviceHours = deviceHours,
                    phoneUnlocks = phoneUnlocks.toIntOrNull() ?: 0,
                    notificationsPerDay = notificationsPerDay.toIntOrNull() ?: 0,
                    socialMediaMinutes = socialMediaMinutes.toIntOrNull() ?: 0,
                    studyMinutes = studyMinutes.toIntOrNull() ?: 0,
                    physicalActivityDays = physicalActivityDays,
                    sleepHours = sleepHours,
                    sleepQuality = sleepQuality
                )
                showConfirmationDialog = true
            },
            // enabled = isFormValid // Update validation logic
        ) {
            Text("Record New Habit")
        }

        if (showConfirmationDialog && predictionResult != null) {
            // After getting a prediction, show a confirmation dialog
            ConfirmationDialog(
                stressLevel = predictionResult!!.stressLevel,
                recommendation = RecommendationEngine(context).getRecommendation(predictionResult),
                onDismiss = {
                    showConfirmationDialog = false
                    // Navigate to the report screen to see the new data
                    navController.navigate("dashboard") {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownInput(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable),
            readOnly = true,
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ConfirmationDialog(stressLevel: String, recommendation: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Record Created!") },
        text = {
            Column {
                Text("Your predicted stress level is: $stressLevel")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = recommendation)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}
