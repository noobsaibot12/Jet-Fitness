package com.example.jetfitnessapp.ui.screens.detailsScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.jetfitnessapp.ui.screens.auth.viewModel.AuthState
import com.example.jetfitnessapp.ui.screens.auth.viewModel.AuthViewModel
import com.example.jetfitnessapp.ui.states.ProgressState
import com.example.jetfitnessapp.ui.states.Token

@Composable
fun DetailsScreen (

    modifier: Modifier,
    authViewModel: AuthViewModel,
    detailViewModel: DetailViewModel,
    token: Token,
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit

) {

    val progressState by detailViewModel.progressState.collectAsState()
    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect ( authState ) {

        if ( authState is AuthState.UnAuthenticated ) {

            navigateToLogin()

        } else if ( authState is AuthState.Error ) {

            Toast.makeText( context , ( (authState as AuthState.Error).message ) , Toast.LENGTH_SHORT ).show()

        }

    }

    Column (

        modifier = modifier.fillMaxSize().padding( 20.dp ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {

        Text( if ( progressState is ProgressState.Success ) "${(progressState as ProgressState.Success).progressList.size}" else "LOADING....." )

        Row (

            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {

            Button(

                onClick = {

                    detailViewModel.getProgress( token.token )

                }

            ) {

                Text( "PROGRESS GRAPH" )

            }

            Button(

                onClick = {

                    navigateToHome()

                }

            ) {

                Text ( "HOME!!" )

            }

        }

    }

//    Text ( text = "DETAILS SCREEN!!" , modifier = modifier )


}