package com.example.jetfitnessapp.ui.screens.auth.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.jetfitnessapp.ui.screens.auth.viewModel.AuthState
import com.example.jetfitnessapp.ui.screens.auth.viewModel.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen (

    modifier: Modifier,
    authViewModel: AuthViewModel,
    navigateToHomeScreen: () -> Unit,
    navigateToSignupScreen: () -> Unit

) {

    val authRequestState by authViewModel.authRequestState.collectAsState()
    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect ( authState ) {

        if ( authState is AuthState.Authenticated ) {

            navigateToHomeScreen()

        } else if ( authState is AuthState.Error ) {

            Toast.makeText( context , ( authState as AuthState.Error ).message , Toast.LENGTH_SHORT ).show()

        }

    }


    Column (

        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Text ( "Login Screen!!" )

        Spacer( Modifier.height( 20.dp ) )

        CustomTextField(

            value = authRequestState.email,
            onValueChange = authViewModel::updateEmail,
            label = "Email"

        )

        Spacer( Modifier.height( 20.dp ) )

        CustomTextField (

            value = authRequestState.password,
            onValueChange = authViewModel::updatePassword,
            label = "Password"

        )

        Spacer( Modifier.height( 10.dp ) )

        Button(

            onClick = { authViewModel.onLogin() }

        ) {

            Text( text = "Login" )

        }

        Spacer( Modifier.height( 10.dp ) )

        TextButton(

            onClick = {

                navigateToSignupScreen()
                authViewModel.resetAuthRequestState()

            }

        ) {

            Text ( "Don't have an account? Signup" )

        }

    }

}

@Composable
fun CustomTextField(value: String, label: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(

        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {

                FocusDirection.Down

            }
        )

    )

}


