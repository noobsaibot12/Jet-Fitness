package com.example.jetfitnessapp.ui.screens.homeScreen.weightGraphScreen

import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetfitnessapp.ui.states.WeightState


@Composable
fun WeightGraph(weightState: WeightState) {
    if (weightState is WeightState.Success && weightState.weightList.isNotEmpty()) {

        // Keep only the last 30 days of data
        val last30Weights = weightState.weightList.takeLast(30)
        val last30Dates = weightState.dateList.takeLast(30)

        if (last30Weights.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No weight data available", fontSize = 18.sp, color = Color.Gray)
            }
            return
        }

        val maxWeight = last30Weights.maxOf { it }
        val minWeight = last30Weights.minOf { it }
        val graphHeight = 300.dp

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(graphHeight)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center // Centers the graph inside the box
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val width = size.width
                val height = size.height
                val spaceBetweenPoints = width / (last30Weights.size - 1).coerceAtLeast(1)
                val weightRange = (maxWeight - minWeight).coerceAtLeast(1)

                val points = last30Weights.mapIndexed { index, weight ->
                    val x = index * spaceBetweenPoints
                    val normalizedWeight = (weight - minWeight) / weightRange.toFloat()
                    val y = height - (normalizedWeight * height) // Invert Y-axis
                    Offset(x, y)
                }

                // Draw Y-axis (Weight Scale)
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, 0f),
                    end = Offset(0f, height),
                    strokeWidth = 4f
                )

                // Draw X-axis (Date Scale)
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, height),
                    end = Offset(width, height),
                    strokeWidth = 4f
                )

                // Draw Graph Line
                for (i in 0 until points.size - 1) {
                    drawLine(
                        color = Color.Green,
                        start = points[i],
                        end = points[i + 1],
                        strokeWidth = 4f
                    )
                }

                // Draw Points
                points.forEach { point ->
                    drawCircle(
                        color = Color.Red,
                        radius = 6f,
                        center = point
                    )
                }

                // Draw Axis Labels
                drawIntoCanvas { canvas ->
                    val paint = TextPaint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 28f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }

                    // Draw Date Labels (X-Axis) with Spacing
                    val maxLabels = 5  // Limit the number of labels
                    val step = (last30Dates.size / maxLabels).coerceAtLeast(1)

                    last30Dates.forEachIndexed { index, date ->
                        if (index % step == 0) { // Skip some labels for readability
                            val x = index * spaceBetweenPoints
                            val y = height + 40f // Adjust y position to prevent clipping
                            canvas.nativeCanvas.drawText(date, x, y, paint)
                        }
                    }

                    // Draw Weight Labels (Y-Axis)
                    for (i in 0..4) { // 5 weight levels
                        val weightValue = minWeight + (weightRange / 4f) * i
                        val y = height - (i / 4f) * height
                        canvas.nativeCanvas.drawText(weightValue.toInt().toString(), -40f, y, paint)
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No weight data available", fontSize = 18.sp, color = Color.Gray)
        }
    }
}