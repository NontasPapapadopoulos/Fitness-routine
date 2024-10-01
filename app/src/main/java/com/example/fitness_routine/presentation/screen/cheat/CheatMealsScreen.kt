package com.example.fitness_routine.presentation.screen.cheat

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.presentation.component.BottomBar
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.navigation.Screen


@Composable
fun CheatMealsScreen(
    viewModel: CheatMealsViewModel = hiltViewModel(),
    navigateToScreen: (Screen) -> Unit,
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect { error ->
            Toast.makeText(
                context,
                error.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is CheatMealsState.Content -> {
            CheatMealsContent(
                content = state,
                navigateToScreen = navigateToScreen
            )
        }
        CheatMealsState.Idle -> {
            LoadingBox()
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CheatMealsContent(
    content: CheatMealsState.Content,
    navigateToScreen: (Screen) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "Fitness Diary")

                    }
                },

            )
        },
        bottomBar = {
            BottomBar(
                onClick = { navigateToScreen(it) },
                currentScreen = Screen.Cheat
            )
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Cheat Meals")
        }

    }
}




@Composable
@Preview
private fun CheatMealsContentPreview() {
    CheatMealsContent(
        content = CheatMealsState.Content(
            xx = "xx"
        ),
        navigateToScreen = {}
    )
}