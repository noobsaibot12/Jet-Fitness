package com.example.jetfitnessapp.domain.repository

import com.example.jetfitnessapp.data.remote.models.Progress
import com.example.jetfitnessapp.data.remote.models.WorkoutNameAndWorkout
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    suspend fun serverCheck () : String
    suspend fun login ( email: String , password: String ) : String
    suspend fun signup ( email: String , password: String ) : String
    suspend fun isAuthenticated ( token: String ) : Boolean
    suspend fun getProgress ( token: String ) : List<Progress?>
    suspend fun getWeight ( token: String ) : List<Int>
    suspend fun getWorkouts ( token: String ) : List<WorkoutNameAndWorkout>
    suspend fun postProgress ( token: String , progress: Progress ) : String
    suspend fun getDate ( token: String ) : List<String>



    fun getToken (): Flow< String? >
    suspend fun clearToken (): Boolean

}