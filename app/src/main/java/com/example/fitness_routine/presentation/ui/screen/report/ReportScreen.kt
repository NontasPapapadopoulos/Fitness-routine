package com.example.fitness_routine.presentation.ui.screen.report

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.R
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.NoteDomainEntity
import com.example.fitness_routine.domain.entity.enums.Cardio
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing2
import com.example.fitness_routine.presentation.ui.theme.contentSpacing3
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4
import com.example.fitness_routine.presentation.util.asTextFieldValue
import com.example.fitness_routine.presentation.util.toFormattedDate
import java.util.Date
import java.util.UUID

@Composable
fun ReportScreen(
    viewModel: ReportViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToWorkout: (Long) -> Unit,
    navigateToBodyMeasurement: (Long) -> Unit,
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
        is ReportState.Content -> {
            Content(
                content = state,
                navigateBack = navigateBack,
                navigateToWorkout = { navigateToWorkout(it) },
                onUpdateCheckField = { isChecked, field -> viewModel.add(ReportEvent.UpdateCheckBox(isChecked = isChecked, checkBoxField = field)) },
                onUpdateTextField = { value, field ->  viewModel.add(ReportEvent.UpdateField(value = value, field = field)) },
                onSelectMuscle = { viewModel.add(ReportEvent.SelectMuscle(it)) },
                onAddCardio = { viewModel.add(ReportEvent.AddCardio) },
                onUpdateCardio = { cardio, field, value -> viewModel.add(ReportEvent.UpdateCardio(cardio, field, value))},
                onDeleteCardio = { viewModel.add(ReportEvent.DeleteCardio(it)) },
                onAddCheatMeal = { viewModel.add(ReportEvent.AddCheatMeal) },
                onUpdateCheatMeal = { meal, value -> viewModel.add(ReportEvent.UpdateCheatMeal(meal, value)) },
                onDeleteCheatMeal = { viewModel.add(ReportEvent.DeleteCheatMeal(it)) },
                onAddNote = { viewModel.add(ReportEvent.AddNote) },
                onUpdateNote = { note, value -> viewModel.add(ReportEvent.UpdateNote(note, value)) },
                onDeleteNote = { viewModel.add(ReportEvent.DeleteNote(it)) },
                navigateToBodyMeasurement = { navigateToBodyMeasurement(it) }
            )
        }

        ReportState.Idle -> { LoadingBox() }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    content: ReportState.Content,
    navigateBack: () -> Unit,
    navigateToWorkout: (Long) -> Unit,
    onUpdateCheckField: (Boolean, CheckBoxField) -> Unit,
    onUpdateTextField: (String, Field) -> Unit,
    onSelectMuscle: (String) -> Unit,
    onAddCardio: () -> Unit,
    onUpdateCardio: (CardioDomainEntity, CardioField, String) -> Unit,
    onDeleteCardio: (CardioDomainEntity) -> Unit,
    onAddCheatMeal: () -> Unit,
    onUpdateCheatMeal: (CheatMealDomainEntity, String) -> Unit,
    onDeleteCheatMeal: (CheatMealDomainEntity) -> Unit,
    onAddNote: () -> Unit,
    onUpdateNote: (NoteDomainEntity, String) -> Unit,
    onDeleteNote: (NoteDomainEntity) -> Unit,
    navigateToBodyMeasurement: (Long) -> Unit
) {

    var selectedTabIndex by remember { mutableIntStateOf(1) }
    val tabs = listOf(Tab.Workout, Tab.Nutrition)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Fitness Diary")

                        Text(text = content.date.toFormattedDate())
//                        Icon(Icons.Default.Delete,
//                            contentDescription = null,
//                            tint = MaterialTheme.colorScheme.onError,
//                            modifier = Modifier.testTag(DELETE_BUTTON)
//                        )
                    }
                },
                navigationIcon = { BackButton(navigateBack) }
            )
        },
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentSpacing4)
        ) {

            Tabs(
                selectedTabIndex = selectedTabIndex,
                selectTab = { selectedTabIndex = it },
                tabs = tabs
            )

            val selectedTab = tabs[selectedTabIndex]
            when (selectedTab) {
                is Tab.Nutrition -> {
                     NutritionTab(
                        dailyReport = content.dailyReport,
                        cheatMeals = content.cheatMeals,
                        onUpdateCheckField = { isChecked, field -> onUpdateCheckField(isChecked, field) },
                        onUpdateTextField = { value, field -> onUpdateTextField(value, field) },
                        onAddCheatMeal = onAddCheatMeal,
                        onUpdateMeal = { meal, value -> onUpdateCheatMeal(meal, value) },
                        onDeleteCheatMeal = { onDeleteCheatMeal(it) },
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    BodyMeasurementButton(
                        navigateToBodyMeasurement = { navigateToBodyMeasurement(content.date) }
                    )
                }
                is Tab.Workout -> {
                    WorkoutTab(
                        dailyReport = content.dailyReport,
                        cardios = content.cardios,
                        notes = content.notes,
                        onUpdateCheckField = { isChecked, field -> onUpdateCheckField(isChecked, field) },
                        onSelectMuscle = onSelectMuscle,
                        onAddCardio = onAddCardio,
                        onDeleteCardio = onDeleteCardio,
                        onUpdateCardio = onUpdateCardio,
                        onAddNote = onAddNote,
                        onUpdateNote = { note, value -> onUpdateNote(note, value) },
                        onDeleteNote = { onDeleteNote(it) },
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    WorkoutButton(navigateToWorkout = { navigateToWorkout(content.date) })
                }
            }
        }
    }

}

@Composable
private fun WorkoutButton(
    navigateToWorkout: () -> Unit,
) {
    Button(
        onClick = { navigateToWorkout() },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Workout"
        )
    }
}

@Composable
private fun BodyMeasurementButton(
    navigateToBodyMeasurement: () -> Unit,
) {
    Button(
        onClick = { navigateToBodyMeasurement() },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Body Measurement"
        )
    }
}




@Composable
private fun Tabs(
    selectedTabIndex: Int,
    selectTab: (Int) -> Unit,
    tabs: List<Tab>
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        tabs = {
            tabs.forEachIndexed { index, tab ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(contentSpacing3)
                        .clickable { selectTab(index) }
                ) {
                    Icon(imageVector = tab.icon, contentDescription = null)

                    Spacer(modifier = Modifier.width(contentSpacing2))

                    Text(
                        text = tab.name,
                        style = MaterialTheme.typography.bodyLarge
                    )

                }
            }
        }
    )
}



@Composable
fun ChoiceCheckBoxItem(
    option: Choice,
    isChecked: Boolean,
    onCheckedChange:  (Boolean) -> Unit,
    testTag: String,
    icon: ImageVector
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(contentSpacing2)
            )
            .padding(contentSpacing2),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        val color = when (option) {
            Choice.Workout -> MaterialTheme.colorScheme.primary
            Choice.Creatine -> colorResource(R.color.creatine)
            Choice.Cheat -> colorResource(R.color.cheat_meal)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier =
                Modifier
                    .background(color = color, shape = RoundedCornerShape(contentSpacing4))
                    .padding(contentSpacing3)
            )

            Spacer(modifier = Modifier.width(contentSpacing2))

            Text(
                text = option.value,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge
            )

        }
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.testTag(testTag)
        )

    }

}



@Composable
@Preview
private fun ReportPreview() {
    AppTheme(darkTheme = true) {
        Content(
            content = ReportState.Content(
                date = 1728939600000,
                dailyReport = DailyReportDomainEntity(
                    proteinGrams = "140",
                    sleepQuality = "3",
                    performedWorkout = true,
                    hadCreatine = false,
                    hadCheatMeal = true,
                    musclesTrained = listOf(),
                    litersOfWater = "2.5",
                    date = Date(),
                ),
                cardios = listOf(
                    CardioDomainEntity(
                    id = UUID.randomUUID().toString(),
                        date = Date(),
                        type = Cardio.Walking.name,
                        minutes = "60"
                    ),
                ),
                notes = generateNotes(),
                cheatMeals = generateCheatMeals()
            ),
            navigateBack = {},
            onUpdateTextField = { _, _ -> },
            onUpdateCheckField = { _, _ -> },
            navigateToWorkout = {},
            onSelectMuscle = {},
            onAddCardio = {},
            onUpdateCardio = { _, _, _ ->},
            onDeleteCardio = {},
            onAddNote = {},
            onUpdateNote = {_, _->},
            onDeleteNote = {},
            onAddCheatMeal = {},
            onUpdateCheatMeal = {_, _->},
            onDeleteCheatMeal = {},
            navigateToBodyMeasurement = {}
        )
    }

}

class ReportScreenConstants private constructor() {
    companion object {
        const val WORKOUT_CHECK_BOX = "workout_check_box"
        const val CHEAT_MEAL_CHECK_BOX = "cheat_meal_check_box"
        const val CREATINE_CHECK_BOX = "creatine_check_box"
        const val PROTEIN_TEXT_FIELD = "protein_text_field"
        const val WATER_TEXT_FIELD = "water_text_field"
        const val CARDIO_TEXT_FIELD = "cardio_text_field"
        const val MUSCLE_ITEM = "muscle_item_"
        const val SLEEP_QUALITY = "sleep_quality"
        const val GYM_NOTES_TEXT_FIELD = "gym_notes_text_field"
        const val CHEAT_MEAL_TEXT_FIELD = "cheat_meal_text_field"
        const val DELETE_BUTTON = "delete_button"
        const val CARDIO_DROP_DOWN = "cardio_drop_down"
        const val CARDIO_TYPE_DROP_DOWN_ITEM = "cardio_type_drop_down_item"

    }
}

sealed class Tab(val name: String, val icon: ImageVector) {
    object Nutrition: Tab("Nutrition", Icons.Filled.Restaurant)
    object Workout: Tab("Workout", Icons.Filled.SportsGymnastics)
}

fun generateNotes() =
    (1..3).map {
        NoteDomainEntity(
            id = "",
            note = "my gym notes",
            date = Date()
        )
    }