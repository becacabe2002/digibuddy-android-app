package com.example.digibuddy.di

import android.content.Context
import com.example.digibuddy.core.database.AppDatabase
import com.example.digibuddy.core.network.RetrofitClient
import com.example.digibuddy.data.repository.StressRepository

object AppModule {

    fun provideStressRepository(): StressRepository {
        return StressRepository(RetrofitClient.instance)
    }

    fun provideHistoryDao(context: Context) = AppDatabase.getDatabase(context).historyDao()

    fun provideUserDao(context: Context) = AppDatabase.getDatabase(context).userDao()
}
