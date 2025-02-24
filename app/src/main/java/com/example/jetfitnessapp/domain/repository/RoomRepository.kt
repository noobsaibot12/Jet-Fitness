package com.example.jetfitnessapp.domain.repository

import com.example.jetfitnessapp.data.local.entity.ProgressRoom
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    fun getProgress(): Flow<ProgressRoom?>

    suspend fun addProgress(workoutProgress: ProgressRoom)

    suspend fun deleteProgress()

    suspend fun updateProgress(workoutProgress: ProgressRoom)

}