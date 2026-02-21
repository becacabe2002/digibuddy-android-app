package com.example.digibuddy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_logs")
data class DigitalHabitLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int, // Foreign key to the User
    val timestamp: Long,
    val deviceHours: Float,
    val phoneUnlocks: Int,
    val notificationsPerDay: Int,
    val socialMediaMinutes: Int,
    val studyMinutes: Int,
    val physicalActivityDays: Float,
    val sleepHours: Float,
    val sleepQuality: Float,
    val predictedStressLevel: String // Store the result of the prediction
)
