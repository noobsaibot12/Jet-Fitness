package com.example.jetfitnessapp.ui.screens.uploadProgressScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfitnessapp.data.local.entity.ProgressRoom
import com.example.jetfitnessapp.data.local.entity.WorkoutNameAndWorkoutRoom
import com.example.jetfitnessapp.data.local.entity.WorkoutRoom
import com.example.jetfitnessapp.data.local.entity.WorkoutSetRoom
import com.example.jetfitnessapp.domain.repository.ApiRepository
import com.example.jetfitnessapp.domain.repository.RoomRepository
import kotlinx.coroutines.launch

class UploadProgressViewModel (

    private val apiRepository: ApiRepository,
    private val roomRepository: RoomRepository

) : ViewModel() {

    var progressRoomState by mutableStateOf( ProgressRoomState(

        workout = WorkoutNameAndWorkoutRoom(
            allWorkouts = emptyList(),
            name = ""
        )

    ) )
        private set

    fun updateProgressRoomState (progressRoomState: ProgressRoomState ) {

        this.progressRoomState = progressRoomState

    }


    fun addWorkoutSet ( rep: Int , weight: Int ) {

        val updatedSets = progressRoomState.workout.allWorkouts.lastOrNull()?.sets?.toMutableList() ?: mutableListOf()
        updatedSets.add(WorkoutSetRoom(reps = rep, weight = weight))

        val updatedWorkouts = progressRoomState.workout.allWorkouts.toMutableList()

        if (updatedWorkouts.isNotEmpty()) {

            updatedWorkouts[updatedWorkouts.lastIndex] = updatedWorkouts.last().copy(sets = updatedSets)

        }

        progressRoomState = progressRoomState.copy(

            workout = progressRoomState.workout.copy(allWorkouts = updatedWorkouts)

        )

    }

    fun addWorkoutRoom ( exercise: String ) {

        val updatedWorkouts = progressRoomState.workout.allWorkouts.toMutableList()
        updatedWorkouts.add(WorkoutRoom(exercise = exercise, sets = emptyList()))

        progressRoomState = progressRoomState.copy(

            workout = progressRoomState.workout.copy(allWorkouts = updatedWorkouts)

        )

    }

    fun addToRoom () {

        viewModelScope.launch {

            roomRepository.addProgress(progressRoomState.toProgressRoom())

        }

    }

}

data class ProgressRoomState (

    val id: Int = 0,
    val weight: Int = 0,
    val workout: WorkoutNameAndWorkoutRoom
//    val workout: Work

)

fun ProgressRoomState.toProgressRoom(): ProgressRoom = ProgressRoom (

    id = id,
    weight = weight,
    workout = workout

)