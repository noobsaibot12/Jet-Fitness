package com.example.jetfitnessapp.data.repositoryImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.jetfitnessapp.data.remote.ApiService
import com.example.jetfitnessapp.data.remote.models.Progress
import com.example.jetfitnessapp.domain.repository.ApiRepository
import androidx.datastore.preferences.core.Preferences
import com.example.jetfitnessapp.data.remote.models.WorkoutNameAndWorkout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ApiRepositoryImpl (

    private val apiService: ApiService,
    private val tokenManager: TokenManager

) : ApiRepository {

    override suspend fun serverCheck(): String = apiService.serverCheck()

    override suspend fun login(email: String, password: String): String {

        val token = apiService.login( email = email , password = password )
        tokenManager.saveToken( token )

        return token

    }

    override suspend fun signup(email: String, password: String): String {

        val token = apiService.signup( email = email , password = password )
        tokenManager.saveToken( token )

        return token

    }

    override suspend fun isAuthenticated(token: String): Boolean = apiService.isAuthenticated( token = token )

    override suspend fun getProgress(token: String): List<Progress?> = apiService.getProgress( token = token )

    override suspend fun getWeight(token: String): List<Int> = apiService.getWeight( token = token )

    override suspend fun getWorkouts(token: String): List<WorkoutNameAndWorkout> = apiService.getWorkouts( token = token )

    override suspend fun postProgress(token: String, progress: Progress): String = apiService.postProgress( token = token , progress = progress )

    override suspend fun getDate(token: String): List<String> = apiService.getDate( token = token )





    override fun getToken(): Flow<String?> = tokenManager.getToken()

    override suspend fun clearToken(): Boolean {

        return try {

            tokenManager.clearToken()
            true

        } catch (e: Exception) {

            e.printStackTrace()
            false

        }

    }

}






class TokenManager (

    private val dataStore: DataStore<Preferences>

) {

    companion object {

        val TOKEN_KEY = stringPreferencesKey("auth_token")

    }

    suspend fun saveToken ( token: String ) {

        dataStore.edit { prefs ->

            prefs[ TOKEN_KEY ] = token

        }

    }

    fun getToken(): Flow<String?> {

        return dataStore.data.map { prefs ->

            prefs[ TOKEN_KEY ]

        }

    }

    suspend fun clearToken () {

        dataStore.edit { prefs ->

            prefs.remove( TOKEN_KEY )

        }

    }

}