package com.example.digibuddy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.digibuddy.di.ViewModelFactory
import com.example.digibuddy.features.profile.ProfileCreationScreen
import com.example.digibuddy.features.profile.ProfileSelectionScreen
import com.example.digibuddy.features.profile.ProfileViewModel
import com.example.digibuddy.ui.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val profileViewModel: ProfileViewModel = viewModel(factory = ViewModelFactory(context))
    val users by profileViewModel.users.collectAsState()

    NavHost(navController = navController, startDestination = "profile_selection_screen") {
        composable("profile_selection_screen") { ProfileSelectionScreen(navController) }
        composable("profile_creation_screen") { ProfileCreationScreen(navController) }
        composable(
            "main_screen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId")
            val user = users.find { it.id == userId }
            if (user != null) {
                MainScreen(navController, user)
            } else {
                // Handle user not found
            }
        }
    }
}
