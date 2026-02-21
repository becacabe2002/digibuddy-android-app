package com.example.digibuddy.features.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.digibuddy.features.profile.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, user: User) {
    val context = LocalContext.current
    val profileViewModel: ProfileViewModel = viewModel(factory = ViewModelFactory(context))

    var name by remember { mutableStateOf(user.name) }
    var dateOfBirth by remember { mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(user.dateOfBirth)) }
    var gender by remember { mutableStateOf(user.gender) }
    var region by remember { mutableStateOf(user.region) }
    var incomeLevel by remember { mutableStateOf(user.incomeLevel) }
    var educationLevel by remember { mutableStateOf(user.educationLevel) }
    var dailyRole by remember { mutableStateOf(user.dailyRole) }
    var primaryDeviceType by remember { mutableStateOf(user.primaryDeviceType) }

    val isFormValid = name.isNotBlank() && dateOfBirth.isNotBlank() && gender.isNotBlank() && region.isNotBlank() && incomeLevel.isNotBlank() && educationLevel.isNotBlank() && dailyRole.isNotBlank() && primaryDeviceType.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Edit Your Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        TextField(value = dateOfBirth, onValueChange = { dateOfBirth = it }, label = { Text("Date of Birth (YYYY-MM-DD)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

        DropdownInput(label = "Gender", options = listOf("male", "female"), selectedOption = gender, onOptionSelected = { gender = it })
        DropdownInput(label = "Region", options = listOf("Asia", "Africa", "North America", "Middle East", "Europe", "South America"), selectedOption = region, onOptionSelected = { region = it })
        DropdownInput(label = "Income Level", options = listOf("High", "Lower-Mid", "Low", "Upper-Mid"), selectedOption = incomeLevel, onOptionSelected = { incomeLevel = it })
        DropdownInput(label = "Education Level", options = listOf("High School", "Master", "Bachelor", "PhD"), selectedOption = educationLevel, onOptionSelected = { educationLevel = it })
        DropdownInput(label = "Daily Role", options = listOf("Part-time/Shift", "Full-time Employee", "Caregiver/Home", "Unemployed_Looking", "Student"), selectedOption = dailyRole, onOptionSelected = { dailyRole = it })
        DropdownInput(label = "Primary Device Type", options = listOf("Android", "Laptop", "Tablet", "iPhone"), selectedOption = primaryDeviceType, onOptionSelected = { primaryDeviceType = it })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("profile_selection_screen") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                }
            }
        ) {
            Text("Switch Profile")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                // Create a new User object with the updated information
                val updatedUser = user.copy(
                    name = name,
                    dateOfBirth = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateOfBirth)?.time ?: user.dateOfBirth,
                    gender = gender,
                    region = region,
                    incomeLevel = incomeLevel,
                    educationLevel = educationLevel,
                    dailyRole = dailyRole,
                    primaryDeviceType = primaryDeviceType
                )
                profileViewModel.updateUser(updatedUser)
                navController.popBackStack()
            },
            enabled = isFormValid
        ) {
            Text("Update Profile")
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
