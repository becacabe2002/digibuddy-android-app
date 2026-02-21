package com.example.digibuddy.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digibuddy.core.database.UserDao
import com.example.digibuddy.data.model.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ProfileViewModel(private val userDao: UserDao) : ViewModel() {

    val users: StateFlow<List<User>> = userDao.getAllUsers()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun createUser(
        name: String,
        dateOfBirth: String,
        gender: String,
        region: String,
        incomeLevel: String,
        educationLevel: String,
        dailyRole: String,
        primaryDeviceType: String,
        onUserCreated: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateOfBirth)
            val user = User(
                name = name,
                dateOfBirth = date?.time ?: 0,
                gender = gender,
                region = region,
                incomeLevel = incomeLevel,
                educationLevel = educationLevel,
                dailyRole = dailyRole,
                primaryDeviceType = primaryDeviceType
            )
            val newUserId = userDao.insertUser(user)
            onUserCreated(newUserId)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userDao.updateUser(user)
        }
    }
}
