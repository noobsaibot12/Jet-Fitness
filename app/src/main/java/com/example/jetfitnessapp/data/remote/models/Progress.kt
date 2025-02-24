package com.example.jetfitnessapp.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class Progress(

    val bodyWeight: Int,
    val workouts: WorkoutNameAndWorkout,

    )

@Serializable
data class WorkoutNameAndWorkout(

    val name: String,
    val allWorkouts: List< Workout >

)


@Serializable
data class Workout(

    val exercise: String,
    val sets: List<WorkoutSet>

)

@Serializable
data class WorkoutSet(

    val reps: Int,
    val weight: Int

)