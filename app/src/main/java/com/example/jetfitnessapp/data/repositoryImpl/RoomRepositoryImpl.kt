package com.example.jetfitnessapp.data.repositoryImpl

import com.example.jetfitnessapp.data.local.dao.FitnessDao
import com.example.jetfitnessapp.data.local.entity.ProgressRoom
import com.example.jetfitnessapp.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow

class RoomRepositoryImpl (

    private val fitnessDao: FitnessDao

) : RoomRepository {

    override fun getProgress(): Flow<ProgressRoom?> = fitnessDao.getProgress()

    override suspend fun addProgress(workoutProgress: ProgressRoom) = fitnessDao.addProgress( workoutProgress = workoutProgress )

    override suspend fun deleteProgress() = fitnessDao.deleteProgress()

    override suspend fun updateProgress(workoutProgress: ProgressRoom) = fitnessDao.updateProgress( workoutProgress = workoutProgress )

}