package com.example.jetfitnessapp.ui.screens.auth.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfitnessapp.domain.repository.ApiRepository
import com.example.jetfitnessapp.ui.states.Token
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(

    private val apiRepository: ApiRepository

) : ViewModel() {

    private val _authRequestState = MutableStateFlow( AuthRequestState() )
    val authRequestState = _authRequestState.asStateFlow()

    fun updateEmail( newEmail: String ) {

        _authRequestState.value = _authRequestState.value.copy( email = newEmail )

    }

    fun updatePassword( newPassword: String ) {

        _authRequestState.value = _authRequestState.value.copy( password = newPassword )

    }

    fun resetAuthRequestState() {

        _authRequestState.value = _authRequestState.value.copy(

            email = "",
            password = ""

        )

    }





    private val _authState = MutableStateFlow< AuthState >( AuthState.UnAuthenticated )
    val authState = _authState.asStateFlow()

    fun onLogin () {

        _authState.value = AuthState.Loading

        if ( authRequestState.value.email.isEmpty() || authRequestState.value.password.isEmpty() ) {

            _authState.value = AuthState.Error( message = "Email or password is empty" )

        } else {

            viewModelScope.launch {

                val response = apiRepository.login(
                    authRequestState.value.email,
                    authRequestState.value.password
                )

                if (response != "Error in Login") {

                    _authState.value = AuthState.Authenticated

                } else {

                    _authState.value = AuthState.Error(message = response)

                }

            }

        }

    }

    fun onSignup () {

        _authState.value = AuthState.Loading

        if ( authRequestState.value.email.isEmpty() || authRequestState.value.password.isEmpty() ) {

            _authState.value = AuthState.Error( message = "Email or password is empty" )

        } else {

            viewModelScope.launch {

                val response = apiRepository.signup(

                    authRequestState.value.email,
                    authRequestState.value.password

                )

                if (response != "Error in Signup") {

                    _authState.value = AuthState.Authenticated

                } else {

                    _authState.value = AuthState.Error(message = response)

                }

            }

        }

    }

    fun logout() {

        viewModelScope.launch {

            try {

                val response = apiRepository.clearToken()

                if ( response ) {

                    _authState.value = AuthState.UnAuthenticated

                } else {

                    _authState.value = AuthState.Error( "Logout Failed" )

                }

            } catch ( e: Exception ) {

                _authState.value = AuthState.Error("Logout Failed: ${e.message}")

            }

        }

    }




     val token: StateFlow<Token> = apiRepository.getToken()
        .map {

            if ( it != null ) {

                Token(

                    token = it

                )

            } else {

                Token(

                    token = "TOKEN NOT FOUND"

                )

            }

        }
        .stateIn(

            scope = viewModelScope ,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS) ,
            initialValue = Token()

        )

    private fun isAuthenticated() {

        viewModelScope.launch {

            token.collect { userState ->

                userState.token?.let { tokenValue ->

                    if ( tokenValue.isNotEmpty() && tokenValue != "TOKEN NOT FOUND" ) {

                        val response = apiRepository.isAuthenticated( tokenValue )

                        if ( response ) _authState.value = AuthState.Authenticated
                        else _authState.value = AuthState.UnAuthenticated

                    } else {

                        Log.e("UserViewModel", "Token is invalid or not found")
                        _authState.value = AuthState.UnAuthenticated

                    }

                }

            }

        }

    }


    init {

        isAuthenticated()

    }

    companion object {

        private const val TIMEOUT_MILLIS = 5_000L

    }

}

data class AuthRequestState (

    val email: String = "",
    val password: String = "",

)

sealed class AuthState {

    data object UnAuthenticated: AuthState()
    data object Authenticated: AuthState()
    data object Loading: AuthState()
    data class Error( val message: String ): AuthState()

}

