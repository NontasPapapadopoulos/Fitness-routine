package com.example.fitness_routine.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.fitness_routine.presentation.screen.SplashScreen
import com.example.fitness_routine.presentation.screen.calendar.CalendarScreen
import com.example.fitness_routine.presentation.screen.report.ReportScreen
import kotlinx.coroutines.delay


const val ROOT_GRAPH_ROUTE = "root"

@Composable
fun RootNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        route = ROOT_GRAPH_ROUTE,
        startDestination = Screen.Splash.name
    ) {

        composable(
            route = Screen.Splash.name
        ) {

            DisplaySplashScreen(
                navController = navController,
                milliseconds = 200L,
                route = Screen.Calendar.name
            )

            SplashScreen()
        }

        composable(
            route = Screen.Calendar.name
        ) {

            CalendarScreen()
        }


        composable(
            route = Screen.Report.name
        ) {

            ReportScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

    }

}



@Composable
fun DisplaySplashScreen(
    navController: NavController,
    milliseconds: Long,
    route: String
) {
    LaunchedEffect(Unit) {
        delay(milliseconds)
        navController.navigate(route = route)
    }

}
