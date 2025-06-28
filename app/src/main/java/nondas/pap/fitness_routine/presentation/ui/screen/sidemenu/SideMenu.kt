package nondas.pap.fitness_routine.presentation.ui.screen.sidemenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nondas.pap.fitness_routine.presentation.navigation.NavigationTarget
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing2
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing3
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
 fun SideMenu(
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    navigateToScreen: (NavigationTarget) -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.width(250.dp),
        drawerContainerColor = MaterialTheme.colorScheme.background,
        drawerTonalElevation = 0.dp
    ) {
        Column {

            Icon(
                Icons.Filled.FitnessCenter,
                null,
                modifier = Modifier
                    .padding(contentSpacing4)
                    .size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Your Fitness Tracker",
                modifier = Modifier.padding(start = contentSpacing4, bottom = contentSpacing4),
                style = MaterialTheme.typography.headlineLarge
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Exercises",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                selected = false,
                onClick = {
                    coroutineScope.launch { toggleDrawerState(drawerState) }
                    navigateToScreen(NavigationTarget.Exercise)
                },
            )


            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Settings",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                selected = false,
                onClick = {
                    coroutineScope.launch { toggleDrawerState(drawerState) }
                    navigateToScreen(NavigationTarget.Settings)
                }
            )


            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Measurements",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                selected = false,
                onClick = {
                    coroutineScope.launch { toggleDrawerState(drawerState) }
                    navigateToScreen(NavigationTarget.Measurements)
                }
            )


            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Notes",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                selected = false,
                onClick = {
                    coroutineScope.launch { toggleDrawerState(drawerState) }
                    navigateToScreen(NavigationTarget.Notes)
                }
            )

//                    NavigationDrawerItem(
//                        label = {
//                            Text(
//                                text = "Analytics",
//                                color = MaterialTheme.colorScheme.secondary,
//                                style = MaterialTheme.typography.titleLarge
//                            ) },
//                        selected = false,
//                        onClick = {
//                            coroutineScope.launch { toggleDrawerState(drawerState) }
//                            navigateToScreen(Screen.Analytics)
//                        }
//                    )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "version: 1.0.0",
                modifier = Modifier.padding(start = contentSpacing3, bottom = contentSpacing2),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

suspend fun toggleDrawerState(drawerState: DrawerState) {
    if (drawerState.isOpen)
        drawerState.close()
    else drawerState.open()
}

