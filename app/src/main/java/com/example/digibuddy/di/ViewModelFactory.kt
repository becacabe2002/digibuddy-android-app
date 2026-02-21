package com.example.digibuddy.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digibuddy.features.dashboard.DashboardViewModel
import com.example.digibuddy.features.log.LogViewModel
import com.example.digibuddy.features.profile.ProfileViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(AppModule.provideHistoryDao(context)) as T
        }
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(AppModule.provideUserDao(context)) as T
        }
        if (modelClass.isAssignableFrom(LogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LogViewModel(AppModule.provideStressRepository(), AppModule.provideHistoryDao(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
