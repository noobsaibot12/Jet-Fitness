package com.example.jetfitnessapp.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.jetfitnessapp.data.local.database.FitnessDatabase
import com.example.jetfitnessapp.data.remote.ApiService
import com.example.jetfitnessapp.data.remote.KtorClientFactory
import com.example.jetfitnessapp.data.repositoryImpl.ApiRepositoryImpl
import com.example.jetfitnessapp.data.repositoryImpl.RoomRepositoryImpl
import com.example.jetfitnessapp.data.repositoryImpl.TokenManager
import com.example.jetfitnessapp.domain.repository.ApiRepository
import com.example.jetfitnessapp.domain.repository.RoomRepository
import com.example.jetfitnessapp.ui.screens.auth.viewModel.AuthViewModel
import com.example.jetfitnessapp.ui.screens.detailsScreen.DetailViewModel
import com.example.jetfitnessapp.ui.screens.homeScreen.progressGraph.ProgressGraphViewModel
import com.example.jetfitnessapp.ui.screens.homeScreen.homeScreen.HomeScreenViewModel
import com.example.jetfitnessapp.ui.screens.homeScreen.weightGraphScreen.WeightGraphViewModel
import com.example.jetfitnessapp.ui.screens.uploadProgressScreen.UploadProgressViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val TOKEN_KEY = "auth_token"
private val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_KEY)


val appModule = module {

    single { KtorClientFactory.client }





    //ROOM
    single { FitnessDatabase.getDatabase( get() ) }
    single { get< FitnessDatabase >().fitnessDao() }
    single < RoomRepository> { RoomRepositoryImpl( get() ) }








    single { ApiService( get() ) }

    single { TokenManager( get<Application>().dataStore ) }

    single < ApiRepository > { ApiRepositoryImpl( get() , get() ) }

    viewModel { HomeScreenViewModel( get() , get() ) }
    viewModel { AuthViewModel( get() ) }
    viewModel { WeightGraphViewModel( get() ) }
    viewModel { ProgressGraphViewModel( get() ) }
    viewModel { DetailViewModel( get() ) }
    viewModel { UploadProgressViewModel( get() , get() ) }

}