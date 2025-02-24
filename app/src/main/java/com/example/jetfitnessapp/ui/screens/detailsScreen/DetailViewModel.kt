package com.example.jetfitnessapp.ui.screens.detailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfitnessapp.domain.repository.ApiRepository
import com.example.jetfitnessapp.ui.states.ProgressState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(

    private val apiRepository: ApiRepository

) : ViewModel() {

    //PROGRESS STATE
    private val _progressState = MutableStateFlow<ProgressState>(ProgressState.Loading)
    val progressState = _progressState.asStateFlow()

    fun getProgress( token: String? ) {

        _progressState.value = ProgressState.Loading

        viewModelScope.launch {

            if ( token != null ) {

                val response = apiRepository.getProgress( token )
                _progressState.value = ProgressState.Success(progressList = response)

            } else {

                _progressState.value = ProgressState.Error("TOKEN NOT FOUND")

            }

        }

    }

}