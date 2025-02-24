package com.example.jetfitnessapp.ui.screens.uploadProgressScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.jetfitnessapp.ui.screens.auth.viewModel.AuthState
import com.example.jetfitnessapp.ui.screens.auth.viewModel.AuthViewModel

@Composable
fun UploadProgressScreen (

    modifier: Modifier,
    authViewModel: AuthViewModel,
    uploadProgressViewModel: UploadProgressViewModel,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit

) {

    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current
    val progressRoomState = uploadProgressViewModel.progressRoomState

    LaunchedEffect(authState) {

        if (authState is AuthState.UnAuthenticated) {

            navigateToLogin()

        } else if (authState is AuthState.Error) {

            Toast.makeText(context, ((authState as AuthState.Error).message), Toast.LENGTH_SHORT)
                .show()

        }

    }


    var rep by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var exerciseName by rememberSaveable { mutableStateOf("") }
    var weightChecked by rememberSaveable { mutableStateOf(false) }
    var workoutNameChecked by rememberSaveable { mutableStateOf(false) }
    var showUploadButton by rememberSaveable { mutableStateOf(false) }

    Column (

        modifier = modifier.fillMaxWidth().padding( 10.dp ).verticalScroll( rememberScrollState() ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Row (

            modifier = Modifier.fillMaxWidth().padding( 10.dp ),
            verticalAlignment = Alignment.CenterVertically

        ) {

            OutlinedTextField(

                value = progressRoomState.weight.toString(),
                onValueChange = {

                    it.toIntOrNull()?.let { validWeight ->

                        uploadProgressViewModel.updateProgressRoomState(

                            progressRoomState.copy(weight = validWeight)

                        )

                    }

                },
                singleLine = true,
                label = { Text(text = "Weight") },
                readOnly = weightChecked,
                modifier = Modifier.weight( 0.7f )

            )

            Checkbox(

                checked = weightChecked,
                onCheckedChange = {

                    weightChecked = !weightChecked

                },
                modifier = Modifier.weight( 0.3f )

            )

        }

        Spacer( Modifier.height( 10.dp ) )

        Row (

            modifier = Modifier.fillMaxWidth().padding( 10.dp ),
            verticalAlignment = Alignment.CenterVertically

        ) {

            OutlinedTextField(

                value = progressRoomState.workout.name,
                onValueChange = { uploadProgressViewModel.updateProgressRoomState( progressRoomState = progressRoomState.copy( workout = progressRoomState.workout.copy( name = it ) ) ) },
                singleLine = true,
                label = { Text(text = "Workout Name") },
                readOnly = workoutNameChecked,
                modifier = Modifier.weight( 0.7f )

            )

            Checkbox(

                checked = workoutNameChecked,
                onCheckedChange = {

                    workoutNameChecked = !workoutNameChecked

                },
                modifier = Modifier.weight( 0.3f )

            )

        }

        Spacer( Modifier.height( 20.dp ) )

        if ( progressRoomState.weight != 0 && progressRoomState.workout.name.isNotEmpty() ) {

            Column (

                modifier = Modifier.fillMaxWidth().padding( 20.dp ),
                verticalArrangement = Arrangement.Center

            ) {

                Row (

                    modifier = Modifier.fillMaxWidth().padding( 5.dp ),
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    OutlinedTextField(
                        value = exerciseName,
                        onValueChange = { exerciseName = it },
                        singleLine = true,
                        label = { Text(text = "Exercise Name") },
                        modifier = Modifier.weight(0.6f)
                    )

                    Spacer ( Modifier.width( 10.dp ) )

                    Button(
                        onClick = {
                            if (exerciseName.isNotBlank()) {
                                uploadProgressViewModel.addWorkoutRoom(exerciseName)
                                exerciseName = ""  // Clear after adding
                            }
                        },
                        modifier = Modifier.weight(0.3f)
                    ) {
                        Text(text = "Add Exercise")
                    }

                }

                Row (

                    modifier = Modifier.fillMaxWidth().padding( 5.dp ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    OutlinedTextField(

                        value = rep,
                        onValueChange = { rep = it },
                        singleLine = true,
                        label = { Text(text = "Total Rep") },
                        modifier = Modifier.weight( 0.5f )

                    )

                    Spacer( Modifier.width( 10.dp ) )

                    OutlinedTextField(

                        value = weight,
                        onValueChange = { weight = it },
                        singleLine = true,
                        label = { Text(text = "WEIGHT") },
                        modifier = Modifier.weight( 0.5f )

                    )

                }

                Button(
                    onClick = {
                        val repValue = rep.toIntOrNull()
                        val weightValue = weight.toIntOrNull()
                        if (repValue != null && weightValue != null) {
                            uploadProgressViewModel.addWorkoutSet(repValue, weightValue)
                            rep = ""
                            weight = ""
                        }

                        showUploadButton = true

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add Workout Set")
                }

            }

        }

        Spacer( Modifier.height( 20.dp ) )
        Spacer( Modifier.height( 20.dp ) )
        Spacer( Modifier.height( 20.dp ) )

        if ( showUploadButton ) {

            Button(

                onClick = {

                    uploadProgressViewModel.addToRoom()
                    navigateToHome()

                }

            ){

                Text(text = "Upload Progress")

            }

        }

    }

}