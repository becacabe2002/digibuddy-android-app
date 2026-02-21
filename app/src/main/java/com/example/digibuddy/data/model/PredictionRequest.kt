package com.example.digibuddy.data.model

import com.google.gson.annotations.SerializedName

data class PredictionRequest(
    @SerializedName("age") val age: Int,
    @SerializedName("gender") val gender: String,
    @SerializedName("region") val region: String,
    @SerializedName("income_level") val income_level: String,
    @SerializedName("education_level") val education_level: String,
    @SerializedName("daily_role") val daily_role: String,
    @SerializedName("device_hours_per_day") val device_hours_per_day: Float,
    @SerializedName("phone_unlocks") val phone_unlocks: Int,
    @SerializedName("notifications_per_day") val notifications_per_day: Int,
    @SerializedName("social_media_mins") val social_media_mins: Int,
    @SerializedName("study_mins") val study_mins: Int,
    @SerializedName("physical_activity_days") val physical_activity_days: Float,
    @SerializedName("sleep_hours") val sleep_hours: Float,
    @SerializedName("sleep_quality") val sleep_quality: Float,
    @SerializedName("device_type") val device_type: String
)
