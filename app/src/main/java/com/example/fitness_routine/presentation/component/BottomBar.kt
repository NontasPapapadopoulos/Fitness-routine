package com.example.fitness_routine.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitness_routine.presentation.navigation.Screen
import com.example.fitness_routine.presentation.ui.icons.FitnessDiary
import com.example.fitness_routine.presentation.ui.icons.myiconpack.FitnessTracker24px

@Composable
fun BottomBar(
    onClick: (Screen) -> Unit,
    currentScreen: Screen
) {

    val choices = listOf(
        BottomBarChoice(
            icon = Icons.Filled.CalendarMonth,
            text = "Calendar",
            screen = Screen.Calendar
        ),
        BottomBarChoice(
            icon = Icons.Default.FitnessCenter,
            text = "Workout",
            screen = Screen.Workout
        ),
        BottomBarChoice(
            icon = FitnessDiary.FitnessTracker24px,
            text = "Gym",
            screen = Screen.Gym
        ),
        BottomBarChoice(
            icon = Icons.Filled.Fastfood,
            text = "Cheat",
            screen = Screen.Cheat
        )
    )

    Surface(
        tonalElevation = 2.dp,
        shadowElevation = 2.dp
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            choices.forEach {
                val isSelected = it.screen == currentScreen

                Column(
                    modifier = Modifier.clickable { onClick(it.screen) }
                        .testTag(it.screen.name),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        it.icon,
                        contentDescription = null,
                        tint = if (isSelected) Color.Red else Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it.text,
                        color = if (isSelected) Color.Red else Color.Black
                    )
                }

            }

        }
    }
}


data class BottomBarChoice(
    val icon: ImageVector,
    val text: String,
    val screen: Screen
)

@Composable
@Preview
private fun BottomBarPreview() {
    BottomBar(
        onClick = {},
        currentScreen = Screen.Calendar,
    )
}