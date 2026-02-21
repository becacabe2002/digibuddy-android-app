package com.example.digibuddy.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.digibuddy.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM user_profiles")
    fun getAllUsers(): Flow<List<User>>

    @Update
    suspend fun updateUser(user: User)
}
