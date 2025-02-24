package com.example.jetfitnessapp.ui.screens.homeScreen.homeScreen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.jetfitnessapp.ui.screens.auth.viewModel.AuthState
import com.example.jetfitnessapp.ui.screens.auth.viewModel.AuthViewModel
import com.example.jetfitnessapp.ui.screens.homeScreen.progressGraph.ProgressGraph
import com.example.jetfitnessapp.ui.screens.homeScreen.progressGraph.ProgressGraphViewModel
import com.example.jetfitnessapp.ui.screens.homeScreen.weightGraphScreen.WeightGraph
import com.example.jetfitnessapp.ui.screens.homeScreen.weightGraphScreen.WeightGraphViewModel
import com.example.jetfitnessapp.ui.states.Token

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen (

    modifier: Modifier,
    authViewModel: AuthViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    weightGraphViewModel: WeightGraphViewModel,
    progressGraphViewModel: ProgressGraphViewModel,
    token: Token,
    showWeightState: Boolean,
    navigateToLoginScreen: () -> Unit,

) {

    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current

    val workoutState by progressGraphViewModel.workoutState.collectAsState()
    val weightState by weightGraphViewModel.weightState.collectAsState()

    val demoState = homeScreenViewModel.demoState

    LaunchedEffect ( authState ) {

        if ( authState is AuthState.UnAuthenticated ) {

            navigateToLoginScreen()

        } else if ( authState is AuthState.Error ) {

            Toast.makeText( context , ( authState as AuthState.Error ).message , Toast.LENGTH_SHORT ).show()

        }

    }

    var showTodayWorkout by rememberSaveable { mutableStateOf(false) }

    Column (

        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll( rememberScrollState() ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {

        if ( showWeightState ) {

            Box(

                Modifier
                    .height( 400.dp )
                    .background( Color.DarkGray )
                    .padding( 10.dp ),
                contentAlignment = Alignment.Center

            ) {

               WeightGraph(

                   weightState = weightState

               )

            }

        } else {

            Box(

                Modifier
                    .height( 400.dp )
                    .background( Color.DarkGray )
                    .padding( 10.dp )

            ) {

                ProgressGraph(

                    workoutState = workoutState

                )

            }

        }

        Row (

            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {

            Button(

                onClick = {

                    homeScreenViewModel.changeShowWeightState( true )
                    weightGraphViewModel.getWeight( token.token )

                },
                modifier = Modifier.weight( 0.5f ).padding( 5.dp ),
                shape = RoundedCornerShape( CornerSize( 2.dp ) ),
//                colors = if ( showWeightState )  ButtonDefaults.buttonColors( Color.Green ) else ButtonDefaults.buttonColors( Color.Red )

            ) {

                Text ( "WEIGHT GRAPH" )

            }

            Button(

                onClick = {

                    homeScreenViewModel.changeShowWeightState( false )
                    progressGraphViewModel.getWorkouts( token.token )

                },
                modifier = Modifier.weight( 0.5f ).padding( 5.dp ),
                shape = RoundedCornerShape( CornerSize( 2.dp ) ),
//                colors = if ( !showWeightState )  ButtonDefaults.buttonColors( Color.Green ) else ButtonDefaults.buttonColors( Color.Red )

            ) {

                Text( "PROGRESS GRAPH" )

            }

        }

        Spacer( Modifier.height( 50.dp ) )

        Button(

            onClick = {

                homeScreenViewModel.fetchProgressRoomState()
                showTodayWorkout = !showTodayWorkout

            },
            modifier = Modifier.fillMaxWidth().padding( 10.dp ),
            shape = RoundedCornerShape( CornerSize( 2.dp ) )

        ) {

            Text ( "TODAY'S WORKOUT!!" )

        }

        if ( showTodayWorkout ) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding( 10.dp ),
                contentAlignment = Alignment.Center

            ) {

                Column (

                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {

                    Text ( if (demoState.id != 0) "${demoState.weight} and ${demoState.workout}" else "Loading....." )

                    Spacer( Modifier.height( 10.dp ) )

                    Button(

                        onClick = {

                            homeScreenViewModel.deleteDemo()
                            showTodayWorkout = !showTodayWorkout

                        },
                        modifier = Modifier.fillMaxWidth().padding( 10.dp ),
                        shape = RoundedCornerShape( CornerSize( 2.dp ) )

                    ) {

                        Text ( "DELETE TODAY'S WORKOUT!!" )

                    }

                }

            }

        }

        Spacer ( Modifier.height( 20.dp ) )

        Button(

            onClick = {

                homeScreenViewModel.uploadProgressToServer( token = token.token )

            },
            modifier = Modifier.fillMaxWidth().padding( 10.dp ),
            shape = RoundedCornerShape( CornerSize( 2.dp ) )

        ) {

            Text ( "UPLOAD TO BACKEND!!" )

        }

    }

}

//@Composable
//fun WaitScreen (
//
//    modifier: Modifier,
//    progressState: ProgressState,
//    navigateToLoginScreen: () -> Unit,
//    token: Token,
//    homeScreenViewModel: HomeScreenViewModel
//
//) {
//
//    Column (
//
//        modifier = modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//
//    ) {
//
//        if ( progressState is ProgressState.Loading) {
//
//            Text ( "LOADING SCREEN........$progressState" )
//
//        } else if ( progressState is ProgressState.Error) {
//
//            Text ( "ERROR SCREEN........${progressState.message}" )
//
//        }
//
//    }
//
//}
//
//@Composable
//fun ResultScreen (
//
//    modifier: Modifier,
//    progressList: List< Progress? >,
//    navigateToLoginScreen: () -> Unit,
//    token: Token,
//    homeScreenViewModel: HomeScreenViewModel
//
//){
//
//    Column (
//
//        modifier = modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//
//    ) {
//
//        Text ( "${progressList.size}" )
//
//    }
//
//}