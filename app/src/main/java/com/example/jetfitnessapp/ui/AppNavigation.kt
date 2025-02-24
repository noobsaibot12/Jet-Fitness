package com.example.jetfitnessapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetfitnessapp.ui.screens.auth.screen.LoginScreen
import com.example.jetfitnessapp.ui.screens.auth.screen.SignupScreen
import com.example.jetfitnessapp.ui.screens.auth.viewModel.AuthViewModel
import com.example.jetfitnessapp.ui.screens.detailsScreen.DetailViewModel
import com.example.jetfitnessapp.ui.screens.detailsScreen.DetailsScreen
import com.example.jetfitnessapp.ui.screens.homeScreen.progressGraph.ProgressGraphViewModel
import com.example.jetfitnessapp.ui.screens.homeScreen.homeScreen.HomeScreen
import com.example.jetfitnessapp.ui.screens.homeScreen.homeScreen.HomeScreenViewModel
import com.example.jetfitnessapp.ui.screens.homeScreen.weightGraphScreen.WeightGraphViewModel
import com.example.jetfitnessapp.ui.screens.uploadProgressScreen.UploadProgressScreen
import com.example.jetfitnessapp.ui.screens.uploadProgressScreen.UploadProgressViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation () {

    val navController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()
    val token by authViewModel.token.collectAsState()


    Scaffold (

        modifier = Modifier.fillMaxSize(),
        topBar = {
//
//            val currentRoute = navController.currentBackStackEntry?.destination?.route
//            val showLogout = currentRoute != "login" && currentRoute != "signup"

            FitnessTopAppBar(

                navController = navController,
                authViewModel = authViewModel

            )

        },
        bottomBar = {

            FitnessBottomAppBar(

                navController = navController,
                authViewModel = authViewModel

            )

        }

    ) { innerPadding ->

        NavHost(

            navController = navController,
            startDestination = "login"

        ) {

            composable(

                route = "login"

            ) {

                LoginScreen(

                    modifier = Modifier.padding( innerPadding ),
                    authViewModel = authViewModel,
                    navigateToHomeScreen = {

                        navController.navigate("home") {

                            popUpTo("login") {
                                inclusive = true
                            } // This clears the back stack up to "login"
                            launchSingleTop = true

                        }

                    },
                    navigateToSignupScreen = {

                        navController.navigate("signup") {

                            popUpTo("login") { inclusive = true }
                            launchSingleTop = true

                        }

                    }

                )

            }

            composable(

                route = "signup"

            ) {

                SignupScreen(

                    modifier = Modifier.padding( innerPadding ),
                    authViewModel = authViewModel,
                    navigateToHomeScreen = {

                        navController.navigate("home") {

                            popUpTo("signup") {
                                inclusive = true
                            } // This clears the back stack up to "login"
                            launchSingleTop = true

                        }

                    },
                    navigateToLoginScreen = {

                        navController.navigate("login") {

                            popUpTo("signup") { inclusive = true }
                            launchSingleTop = true

                        }

                    }

                )

            }

            composable(

                route = "home"

            ) {

                val homeScreenViewModel: HomeScreenViewModel = koinViewModel()
                val showWeightState by homeScreenViewModel.showWeightState
                val weightGraphViewModel: WeightGraphViewModel = koinViewModel()


                val progressGraphViewModel: ProgressGraphViewModel = koinViewModel()

                HomeScreen(

                    modifier = Modifier.padding( innerPadding ),
                    authViewModel = authViewModel,
                    homeScreenViewModel = homeScreenViewModel,
                    weightGraphViewModel = weightGraphViewModel,
                    progressGraphViewModel = progressGraphViewModel,
                    token = token,
                    showWeightState = showWeightState,
                    navigateToLoginScreen = {

                        navController.navigate("login") {

                            popUpTo("home") { inclusive = true }
                            launchSingleTop = true

                        }

                    }

                )

            }

            composable(

                route = "details"

            ) {

                val detailViewModel: DetailViewModel = koinViewModel()

                DetailsScreen(

                    modifier = Modifier.padding( innerPadding ),
                    authViewModel = authViewModel,
                    detailViewModel = detailViewModel,
                    token = token,
                    navigateToHome = {

                        navController.popBackStack()

                    },
                    navigateToLogin = {

                        navController.navigate( "login" ) {

//                        navController.navigate("login") {
//                            popUpTo("home") { inclusive = true } // Removes "home" and everything above it
//                        }

                            //OR

                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true } // Clears the entire back /stack
                            }

                        }

                    }

                )

            }

            composable(

                route = "uploadProgress"

            ) {

                val uploadProgressViewModel: UploadProgressViewModel = koinViewModel()

                UploadProgressScreen(

                    modifier = Modifier.padding( innerPadding ),
                    uploadProgressViewModel = uploadProgressViewModel,
                    authViewModel = authViewModel,
                    navigateToLogin = {

                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true } // Clears the entire back /stack
                        }

                    },
                    navigateToHome = {

                        navController.popBackStack()

                    }

                )

            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessTopAppBar (

    navController: NavController,
    authViewModel: AuthViewModel

) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showLogout = currentRoute != "login" && currentRoute != "signup"

    TopAppBar(

        modifier = Modifier,
        title = {

            Row (

                modifier = Modifier.fillMaxWidth().padding( 10.dp ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Text( "FITNESS APP" )

                if ( showLogout ) {

                    Button(

                        onClick ={

                            authViewModel.logout()
                            authViewModel.resetAuthRequestState()

                        }

                    ) {

                        Text ( "LOGOUT!!" )

                    }

                }

            }

        },


    )

}

@Composable
fun FitnessBottomAppBar (

    navController: NavController,
    authViewModel: AuthViewModel

) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showButton = currentRoute != "login" && currentRoute != "signup"

    if ( showButton ) {

        BottomAppBar(

            modifier = Modifier

        ) {

            Row (

                modifier = Modifier.fillMaxWidth().padding( 10.dp ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                TextButton(

                    onClick ={

                        navController.navigate( "details" )


                    }

                ) {

                    Text ( "DETAILS!!" )

                }

                TextButton(

                    onClick ={

                        navController.navigate( "uploadProgress" )

                    }

                ) {

                    Text ( "ADD PROGRESS!!" )

                }

            }

        }

    }

}