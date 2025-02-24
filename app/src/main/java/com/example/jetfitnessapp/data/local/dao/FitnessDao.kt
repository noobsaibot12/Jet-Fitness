package com.example.jetfitnessapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jetfitnessapp.data.local.entity.ProgressRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface FitnessDao {

    @Query("SELECT * FROM workout_progress LIMIT 1")
    fun getProgress(): Flow<ProgressRoom?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProgress(workoutProgress: ProgressRoom)

    @Query("DELETE FROM workout_progress")
    suspend fun deleteProgress()

    @Update
    suspend fun updateProgress(workoutProgress: ProgressRoom)
}
