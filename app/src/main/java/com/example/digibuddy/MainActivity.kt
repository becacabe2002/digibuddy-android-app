package com.example.digibuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.digibuddy.ui.navigation.AppNavigation
import com.example.digibuddy.ui.theme.DigiBuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigiBuddyTheme {
                AppNavigation()
            }
        }
    }
}
