package com.example.digibuddy.data.model

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @SerializedName("stress_level")
    val stressLevel: String
)
