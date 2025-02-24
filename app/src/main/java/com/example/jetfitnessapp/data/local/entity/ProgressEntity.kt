package com.example.jetfitnessapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.Serializable

// Converters for Room to handle lists
class Converters {

    @TypeConverter
    fun fromWorkoutRoomList(value: List<WorkoutRoom>): String {

        return Gson().toJson(value)

    }

    @TypeConverter
    fun toWorkoutRoomList(value: String): List<WorkoutRoom> {

        val type = object : TypeToken<List<WorkoutRoom>>() {}.type
        return Gson().fromJson(value, type)

    }

//    @TypeConverter
//    fun fromWorkoutNameAndWorkoutRoomList(value: List<WorkoutNameAndWorkoutRoom>): String {
//        return Gson().toJson(value)
//    }
//
//    @TypeConverter
//    fun toWorkoutNameAndWorkoutRoomList(value: String): List<WorkoutNameAndWorkoutRoom> {
//        val type = object : TypeToken<List<WorkoutNameAndWorkoutRoom>>() {}.type
//        return Gson().fromJson(value, type)
//    }

    @TypeConverter
    fun fromWorkoutSetRoomList(value: List<WorkoutSetRoom>): String {

        return Gson().toJson(value)

    }

    @TypeConverter
    fun toWorkoutSetRoomList(value: String): List<WorkoutSetRoom> {

        val type = object : TypeToken<List<WorkoutSetRoom>>() {}.type
        return Gson().fromJson(value, type)

    }

}

@Entity(tableName = "workout_progress")
@TypeConverters(Converters::class)
data class ProgressRoom(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val weight: Int,
    @Embedded val workout: WorkoutNameAndWorkoutRoom

)

@Serializable
data class WorkoutNameAndWorkoutRoom(

    val name: String,
    val allWorkouts: List<WorkoutRoom>

)

@Serializable
data class WorkoutRoom(

    val exercise: String,
    val sets: List<WorkoutSetRoom>

)

@Serializable
data class WorkoutSetRoom(

    val reps: Int,
    val weight: Int

)
