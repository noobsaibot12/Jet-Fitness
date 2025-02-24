package com.example.jetfitnessapp.ui.screens.homeScreen.progressGraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetfitnessapp.ui.states.WorkoutState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProgressGraph(workoutState: WorkoutState) {
    if (workoutState is WorkoutState.Success && workoutState.workoutList.isNotEmpty()) {
        val today = LocalDate.now()
        val last30Days = today.minusDays(30)

        // Ensure dateList and workoutList have the same size
        if (workoutState.dateList.size != workoutState.workoutList.size) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Data mismatch error", fontSize = 18.sp, color = Color.Red)
            }
            return
        }

        // Filter workouts using dateList
        val filteredWorkouts = workoutState.workoutList.zip(workoutState.dateList)
            .filter { (_, dateString) ->
                val workoutDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)
                workoutDate.isAfter(last30Days) || workoutDate.isEqual(last30Days)
            }
            .map { it.first } // Extract only workouts

        if (filteredWorkouts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No progress data in the last 30 days", fontSize = 18.sp, color = Color.Gray)
            }
            return
        }

        val workoutData = filteredWorkouts.associate { workout ->
            val maxSet = workout.allWorkouts.flatMap { it.sets }
                .maxByOrNull { it.weight }

            val maxWeight = maxSet?.weight ?: 0
            val maxExercise = workout.allWorkouts.firstOrNull { it.sets.contains(maxSet) }?.exercise ?: "Unknown"

            workout.name to Pair(maxWeight, maxExercise)
        }

//        val maxWeight = workoutData.values.maxOrNull()?.toFloat() ?: 1f

        val mxWeight = workoutData.values.first().first.toFloat()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll( rememberScrollState() ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Workout Progress (Last 30 Days)", fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))

            workoutData.forEach { (name, data) ->
                val (weight, exercise) = data

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = name, fontSize = 16.sp, color = Color.White)
                            Text(text = "Exercise: $exercise", fontSize = 14.sp, color = Color.Gray)
                        }
                        Box(
                            modifier = Modifier
                                .weight(3f)
                                .height(20.dp)
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                drawLine(
                                    color = Color.Green,
                                    start = Offset(0f, size.height / 2),
                                    end = Offset((size.width * weight / mxWeight), size.height / 2),
                                    strokeWidth = size.height
                                )
                            }
                        }
                        Text(text = "$weight kg", fontSize = 14.sp, color = Color.White, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading...", fontSize = 18.sp, color = Color.Gray)
        }
    }
}

