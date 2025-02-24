package com.example.jetfitnessapp.ui.screens.homeScreen.homeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfitnessapp.data.local.entity.ProgressRoom
import com.example.jetfitnessapp.data.local.entity.WorkoutNameAndWorkoutRoom
import com.example.jetfitnessapp.data.remote.models.Progress
import com.example.jetfitnessapp.data.remote.models.Workout
import com.example.jetfitnessapp.data.remote.models.WorkoutNameAndWorkout
import com.example.jetfitnessapp.data.remote.models.WorkoutSet
import com.example.jetfitnessapp.domain.repository.ApiRepository
import com.example.jetfitnessapp.domain.repository.RoomRepository
import com.example.jetfitnessapp.ui.screens.uploadProgressScreen.ProgressRoomState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class HomeScreenViewModel (

    private val apiRepository: ApiRepository,
    private val roomRepository: RoomRepository

) : ViewModel()  {

    private val _state = mutableStateOf( "" )
    val state: State<String> = _state

    private val _showWeightState = mutableStateOf( true )
    val showWeightState: State< Boolean > = _showWeightState

    fun changeShowWeightState ( newState: Boolean ) {

        _showWeightState.value = newState

    }

    var demoState by mutableStateOf( ProgressRoomState(

        workout = WorkoutNameAndWorkoutRoom(

            name = "",
            allWorkouts = emptyList()

        )

    ) )
        private set

    fun fetchProgressRoomState () {

        viewModelScope.launch ( Dispatchers.IO ) {

            val demo = roomRepository.getProgress().firstOrNull()
//            demoState = demo?.toDemoState() ?: DemoState(workout = emptyList())
            
            if ( demo != null ) {

                demoState = demo.toProgressRoomState()

            } else {

                demoState = ProgressRoomState(

                    workout = WorkoutNameAndWorkoutRoom(

                        name = "",
                        allWorkouts = emptyList()

                    )

                )

            }

        }

    }

    fun deleteDemo () {

        viewModelScope.launch ( Dispatchers.IO ) {

            roomRepository.deleteProgress()
            demoState = ProgressRoomState(

                workout = WorkoutNameAndWorkoutRoom(

                    name = "",
                    allWorkouts = emptyList()

                )

            )

        }

    }


    fun uploadProgressToServer(token: String?) {

        if ( token != null ) {

            viewModelScope.launch(Dispatchers.IO) {

                val progressRoom = roomRepository.getProgress().firstOrNull()

                if (progressRoom != null) {

                    val progress = progressRoom.toProgress()

                    val response = apiRepository.postProgress(token, progress)

                    // Handle response (e.g., update UI state)
                    _state.value = response

                } else {

                    _state.value = "No progress data available"

                }

            }

        } else {

            _state.value = "Token not found"

        }

    }


}

fun ProgressRoom.toProgressRoomState() : ProgressRoomState = ProgressRoomState(

    id = id,
    weight = weight,
    workout = workout

)

fun ProgressRoom.toProgress(): Progress {

    return Progress(

        bodyWeight = this.weight, // You need to replace this with actual user weight data.
        workouts = WorkoutNameAndWorkout(

            name = this.workout.name,
            allWorkouts = this.workout.allWorkouts.map { workoutRoom ->

                Workout(

                    exercise = workoutRoom.exercise,
                    sets = workoutRoom.sets.map { workoutSetRoom ->

                        WorkoutSet(

                            reps = workoutSetRoom.reps,
                            weight = workoutSetRoom.weight

                        )

                    }

                )

            }

        )

    )

}
