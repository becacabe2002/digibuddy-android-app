package com.example.digibuddy.data.repository

import com.example.digibuddy.core.network.ApiService
import com.example.digibuddy.data.model.PredictionRequest

class StressRepository(private val apiService: ApiService) {

    suspend fun getStressPrediction(request: PredictionRequest) = apiService.getStressPrediction(request)
}
