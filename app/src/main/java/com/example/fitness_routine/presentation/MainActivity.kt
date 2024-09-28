package com.example.fitness_routine.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.fitness_routine.presentation.navigation.RootNavGraph
import com.example.fitness_routine.presentation.ui.theme.FitnessroutineTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        enableEdgeToEdge()
        setContent {
            FitnessroutineTheme {
                MakeSystemBarsTransparent()
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.navigationBars)) { innerPadding ->
                    innerPadding

                    RootNavGraph(navController = rememberNavController())

                }
            }
        }
    }



    @Composable
    private fun MakeSystemBarsTransparent() {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
        }
    }

}


