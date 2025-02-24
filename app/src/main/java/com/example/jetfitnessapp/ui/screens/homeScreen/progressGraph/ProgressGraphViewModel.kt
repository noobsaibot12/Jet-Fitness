package com.example.jetfitnessapp.ui.screens.homeScreen.progressGraph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfitnessapp.domain.repository.ApiRepository
import com.example.jetfitnessapp.ui.states.ProgressState
import com.example.jetfitnessapp.ui.states.WorkoutState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProgressGraphViewModel (

    private val apiRepository: ApiRepository

) : ViewModel() {

    private val _workoutState = MutableStateFlow< WorkoutState >( WorkoutState.Loading )
    val workoutState = _workoutState.asStateFlow()

    fun getWorkouts ( token: String? ) {

        _workoutState.value = WorkoutState.Loading

        viewModelScope.launch {

            if ( token != null ) {

                val response = apiRepository.getWorkouts( token )
                val dateList = apiRepository.getDate( token )
                _workoutState.value = WorkoutState.Success( workoutList = response , dateList = dateList )

            } else {

                _workoutState.value = WorkoutState.Error( message = "TOKEN NOT FOUND" )

            }

        }

    }

}