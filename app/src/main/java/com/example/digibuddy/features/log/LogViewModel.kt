package com.example.digibuddy.features.log

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digibuddy.core.database.HistoryDao
import com.example.digibuddy.core.database.HistoryEntity
import com.example.digibuddy.data.model.PredictionRequest
import com.example.digibuddy.data.model.User
import com.example.digibuddy.data.repository.StressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogViewModel(private val stressRepository: StressRepository, private val historyDao: HistoryDao) : ViewModel() {

    private val _predictionResult = MutableStateFlow<HistoryEntity?>(null)
    val predictionResult: StateFlow<HistoryEntity?> = _predictionResult

    fun getStressPrediction(
        userProfile: User,
        deviceHours: Float,
        phoneUnlocks: Int,
        notificationsPerDay: Int,
        socialMediaMinutes: Int,
        studyMinutes: Int,
        physicalActivityDays: Float,
        sleepHours: Float,
        sleepQuality: Float
    ) {
        viewModelScope.launch {
            val request = PredictionRequest(
                age = ((System.currentTimeMillis() - userProfile.dateOfBirth) / (1000L * 60 * 60 * 24 * 365)).toInt(),
                gender = userProfile.gender,
                region = userProfile.region,
                income_level = userProfile.incomeLevel,
                education_level = userProfile.educationLevel,
                daily_role = userProfile.dailyRole,
                device_hours_per_day = deviceHours,
                phone_unlocks = phoneUnlocks,
                notifications_per_day = notificationsPerDay,
                social_media_mins = socialMediaMinutes,
                study_mins = studyMinutes,
                physical_activity_days = physicalActivityDays,
                sleep_hours = sleepHours,
                sleep_quality = sleepQuality,
                device_type = userProfile.primaryDeviceType
            )

            try {
                val response = stressRepository.getStressPrediction(request)
                val historyEntity = HistoryEntity(
                    age = ((System.currentTimeMillis() - userProfile.dateOfBirth) / (1000L * 60 * 60 * 24 * 365)).toInt(),
                    gender = userProfile.gender,
                    region = userProfile.region,
                    income_level = userProfile.incomeLevel,
                    education_level = userProfile.educationLevel,
                    daily_role = userProfile.dailyRole,
                    device_hours_per_day = deviceHours,
                    phone_unlocks = phoneUnlocks,
                    notifications_per_day = notificationsPerDay,
                    social_media_mins = socialMediaMinutes,
                    study_mins = studyMinutes,
                    physical_activity_days = physicalActivityDays,
                    sleep_hours = sleepHours,
                    sleep_quality = sleepQuality,
                    device_type = userProfile.primaryDeviceType,
                    stressLevel = response.stressLevel
                )
                historyDao.insertHistory(historyEntity)
                _predictionResult.value = historyEntity
                Log.d("LogViewModel", "Prediction successful, result: $historyEntity")
            } catch (e: Exception) {
                Log.e("LogViewModel", "Error getting stress prediction", e)
            }
        }
    }
}
