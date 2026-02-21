package com.example.digibuddy.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val age: Int,
    val gender: String,
    val region: String,
    val income_level: String,
    val education_level: String,
    val daily_role: String,
    val device_hours_per_day: Float,
    val phone_unlocks: Int,
    val notifications_per_day: Int,
    val social_media_mins: Int,
    val study_mins: Int,
    val physical_activity_days: Float,
    val sleep_hours: Float,
    val sleep_quality: Float,
    val device_type: String,
    val stressLevel: String,
    val date: Long = System.currentTimeMillis()
)
