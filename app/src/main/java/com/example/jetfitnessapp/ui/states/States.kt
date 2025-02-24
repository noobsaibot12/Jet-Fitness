package com.example.jetfitnessapp.ui.states

import com.example.jetfitnessapp.data.remote.models.Progress
import com.example.jetfitnessapp.data.remote.models.WorkoutNameAndWorkout


data class Token (

    val token: String? = null

)

sealed class ProgressState {

    data object Loading: ProgressState()
    data class Success( val progressList: List<Progress?> ): ProgressState()
    data class Error( val message: String ): ProgressState()

}

sealed class WorkoutState {

    data object Loading: WorkoutState()
    data class Success( val workoutList: List<WorkoutNameAndWorkout> , val dateList: List<String> ): WorkoutState()
    data class Error( val message: String ): WorkoutState()

}

sealed class WeightState {

    data object Loading: WeightState()
    data class Success( val weightList: List<Int> , val dateList: List< String > ): WeightState()
    data class Error( val message: String ): WeightState()

}