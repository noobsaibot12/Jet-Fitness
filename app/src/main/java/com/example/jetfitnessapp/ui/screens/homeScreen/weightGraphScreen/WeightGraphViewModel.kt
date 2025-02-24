package com.example.jetfitnessapp.ui.screens.homeScreen.weightGraphScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfitnessapp.domain.repository.ApiRepository
import com.example.jetfitnessapp.ui.states.WeightState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeightGraphViewModel (

    private val apiRepository: ApiRepository

) : ViewModel() {

    private val _weightState = MutableStateFlow<WeightState>(WeightState.Loading)
    val weightState = _weightState.asStateFlow()

    fun getWeight( token: String? ) {

        _weightState.value = WeightState.Loading

        viewModelScope.launch {

            if ( token != null ) {

                val weightList = apiRepository.getWeight( token )
                val dateList = apiRepository.getDate( token )
                _weightState.value = WeightState.Success(weightList = weightList , dateList = dateList)

            } else {

                _weightState.value = WeightState.Error("TOKEN NOT FOUND")

            }

        }

    }

}