package com.example.fitness_routine.presentation

import android.app.Application
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitnessDiaryApplication: Application() {
}


@AndroidEntryPoint
class HiltComponentActivity: ComponentActivity()