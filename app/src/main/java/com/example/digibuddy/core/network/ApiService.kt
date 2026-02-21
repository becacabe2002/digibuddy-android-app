package com.example.digibuddy.core.network

import com.example.digibuddy.data.model.PredictionRequest
import com.example.digibuddy.data.model.PredictionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("predict")
    suspend fun getStressPrediction(@Body request: PredictionRequest): PredictionResponse
}
