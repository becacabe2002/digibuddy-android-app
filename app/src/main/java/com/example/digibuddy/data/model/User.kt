package com.example.digibuddy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_profiles")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val dateOfBirth: Long, // Store as timestamp
    val gender: String,
    val region: String,
    val incomeLevel: String,
    val educationLevel: String,
    val dailyRole: String,
    val primaryDeviceType: String
)
