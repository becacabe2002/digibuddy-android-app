package com.example.digibuddy.features.profile

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
import com.example.digibuddy.di.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreationScreen(navController: NavController) {
    val context = LocalContext.current
    val profileViewModel: ProfileViewModel = viewModel(factory = ViewModelFactory(context))

    var name by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var incomeLevel by remember { mutableStateOf("") }
    var educationLevel by remember { mutableStateOf("") }
    var dailyRole by remember { mutableStateOf("") }
    var primaryDeviceType by remember { mutableStateOf("") }

    val isFormValid = name.isNotBlank() && dateOfBirth.isNotBlank() && gender.isNotBlank() && region.isNotBlank() && incomeLevel.isNotBlank() && educationLevel.isNotBlank() && dailyRole.isNotBlank() && primaryDeviceType.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create Your Profile", style = MaterialTheme.typography.headlineMedium)
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
                profileViewModel.createUser(
                    name = name,
                    dateOfBirth = dateOfBirth, // This will need to be converted to a timestamp
                    gender = gender,
                    region = region,
                    incomeLevel = incomeLevel,
                    educationLevel = educationLevel,
                    dailyRole = dailyRole,
                    primaryDeviceType = primaryDeviceType
                ) { newUserId ->
                    navController.navigate("main_screen/$newUserId")
                }
            },
            enabled = isFormValid
        ) {
            Text("Create Profile")
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
