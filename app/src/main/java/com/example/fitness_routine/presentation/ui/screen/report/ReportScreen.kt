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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.R
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Cardio
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.component.MusclesTrained
import com.example.fitness_routine.presentation.ui.screen.exercise.ExerciseScreenConstants.Companion.MUSCLE_GROUP_DROPDOWN
import com.example.fitness_routine.presentation.ui.screen.exercise.ExerciseScreenConstants.Companion.MUSCLE_GROUP_DROPDOWN_ITEM
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CARDIO_DROP_DOWN
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CARDIO_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CARDIO_TYPE_DROP_DOWN_ITEM
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CHEAT_MEAL_CHECK_BOX
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CHEAT_MEAL_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CREATINE_CHECK_BOX
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.DELETE_BUTTON
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.GYM_NOTES_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.MUSCLE_ITEM
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.PROTEIN_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.SLEEP_QUALITY
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.WATER_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.WORKOUT_CHECK_BOX
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing2
import com.example.fitness_routine.presentation.ui.theme.contentSpacing3
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4
import com.example.fitness_routine.presentation.util.asTextFieldValue
import com.example.fitness_routine.presentation.util.getIcon
import com.example.fitness_routine.presentation.util.moveCursorToEnd
import com.example.fitness_routine.presentation.util.selectAllPosition
import com.example.fitness_routine.presentation.util.toFormattedDate
import com.example.fitness_routine.presentation.util.updateCursor
import java.util.Date

@Composable
fun ReportScreen(
    viewModel: ReportViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToWorkout: (Long) -> Unit
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
                onSelectMuscle = { viewModel.add(ReportEvent.SelectMuscle(it)) }
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
    onSelectMuscle: (String) -> Unit
) {

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(Tab.Workout, Tab.Nutrition)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "Fitness Diary")

                        Text(text = content.date.toFormattedDate())
                        Icon(Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.testTag(DELETE_BUTTON)
                        )
                    }
                },
                navigationIcon = { BackButton(navigateBack) }
            )
        },

    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(contentSpacing4)
                .verticalScroll(rememberScrollState())
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
                        onUpdateCheckField = { isChecked, field -> onUpdateCheckField(isChecked, field) },
                        onUpdateTextField = { value, field -> onUpdateTextField(value, field) }
                    )
                }
                is Tab.Workout -> {
                    WorkoutTab(
                        dailyReport = content.dailyReport,
                        onUpdateCheckField = { isChecked, field -> onUpdateCheckField(isChecked, field) },
                        onUpdateTextField = { value, field -> onUpdateTextField(value, field) },
                        navigateToWorkout = navigateToWorkout,
                        onSelectMuscle = onSelectMuscle,
                        date = content.date
                    )
                }
            }
        }
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
private fun NutritionTab(
    dailyReport: DailyReportDomainEntity,
    onUpdateCheckField: (Boolean, CheckBoxField) -> Unit,
    onUpdateTextField: (String, Field) -> Unit,
) {
    Spacer(modifier = Modifier.height(contentSpacing2))

    ChoiceItem(
        option = Choice.Creatine,
        isChecked = dailyReport.hadCreatine,
        onCheckedChange = { onUpdateCheckField(it, CheckBoxField.Creatine) },
        testTag = CREATINE_CHECK_BOX,
        icon = Choice.Creatine.getIcon()
    )
    Spacer(modifier = Modifier.height(contentSpacing2))

    ChoiceItem(
        option = Choice.Cheat,
        isChecked = dailyReport.hadCheatMeal,
        onCheckedChange = { onUpdateCheckField(it, CheckBoxField.CheatMeal) },
        testTag = CHEAT_MEAL_CHECK_BOX,
        icon = Choice.Cheat.getIcon()
    )

    Spacer(modifier = Modifier.height(contentSpacing2))

    if (dailyReport.hadCheatMeal) {
        CheatMeal(
            meal = dailyReport.meal,
            onValueChange = { onUpdateTextField(it, Field.CheatMeal) }
        )
    }

    Spacer(modifier = Modifier.height(contentSpacing2))

    SleepQuality(
        level = if (dailyReport.sleepQuality.isNotEmpty()) dailyReport.sleepQuality.toInt() else 0,
        onLevelChange = { onUpdateTextField(it.toString(), Field.SleepQuality) }
    )

    Spacer(modifier = Modifier.height(contentSpacing2))

    Input(
        label = "Protein: ",
        unit = "gr",
        value = dailyReport.proteinGrams,
        onValueChange = { onUpdateTextField(it, Field.ProteinGrams) },
        testTag = PROTEIN_TEXT_FIELD
    )

    Spacer(modifier = Modifier.height(contentSpacing2))


    Input(
        label = "Water: ",
        unit = "Liters",
        value = dailyReport.litersOfWater,
        onValueChange = { onUpdateTextField(it, Field.LitersOfWater) },
        testTag = WATER_TEXT_FIELD
    )

}

@Composable
private fun WorkoutTab(
    dailyReport: DailyReportDomainEntity,
    onUpdateCheckField: (Boolean, CheckBoxField) -> Unit,
    onUpdateTextField: (String, Field) -> Unit,
    navigateToWorkout: (Long) -> Unit,
    onSelectMuscle: (String) -> Unit,
    date: Long
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(contentSpacing2))

        ChoiceItem(
            option = Choice.Workout,
            isChecked = dailyReport.performedWorkout,
            onCheckedChange = { onUpdateCheckField(it, CheckBoxField.Workout) },
            testTag = WORKOUT_CHECK_BOX,
            icon = Choice.Workout.getIcon()
        )

        Spacer(modifier = Modifier.height(contentSpacing2))

        Input(
            label = "Cardio: ",
            unit = "minutes",
            value = dailyReport.cardioMinutes,
            onValueChange = { onUpdateTextField(it, Field.CardioMinutes) },
            testTag = CARDIO_TEXT_FIELD
        )

        if (dailyReport.cardioMinutes.isNotEmpty()) {
            Spacer(modifier = Modifier.height(contentSpacing2))
            CardioType()
        }

        Spacer(modifier = Modifier.height(contentSpacing2))

        MusclesTrained(
            selectedMuscles = dailyReport.musclesTrained,
            onSelectMuscle = { onSelectMuscle(it) },
            testTag = MUSCLE_ITEM
        )

        Spacer(modifier = Modifier.height(contentSpacing2))


        GymNotes(
            notes = dailyReport.gymNotes,
            onValueChange = { onUpdateTextField(it, Field.GymNotes) }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navigateToWorkout(date) },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Note workout details"
            )
        }
    }

}


@Composable
private fun SleepQuality(
    level: Int,
    onLevelChange: (Int) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Sleep quality: ",
            modifier = Modifier.weight(1f)
        )

        Stars(
            level = level,
            onLevelChange = onLevelChange,
            modifier = Modifier.weight(1f)
        )

    }
}




@Composable
private fun GymNotes(
    notes: String,
    onValueChange: (String) -> Unit
) {
    Column {

        OutlinedTextField(
            value = notes.asTextFieldValue(),
            label = { Text(text = "Gym Notes") },
            onValueChange = { onValueChange(it.text) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(GYM_NOTES_TEXT_FIELD)
        )

    }

}


@Composable
private fun CheatMeal(
    meal: String,
    onValueChange: (String) -> Unit
) {
    Column {

        val cursorPosition = remember { mutableStateOf(meal.length) }
        val value = meal.asTextFieldValue()

        OutlinedTextField(
            value = value,
            onValueChange = { textFieldValue ->
                onValueChange(textFieldValue.text)
            },
            singleLine = true,
            label = { Text(text = "Meal") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(CHEAT_MEAL_TEXT_FIELD)
        )

    }

}

@Composable
private fun Stars(
    level: Int,
    onLevelChange: (Int) -> Unit,
    modifier: Modifier
) {
    Row(modifier = modifier) {
        (1..5).forEach {
            val color = if ( it <= level) Color.Yellow else Color.Black

            Star(
                sequence = it,
                color = color,
                onLevelChange = onLevelChange
            )
        }
    }
}


@Composable
private fun Star(
    sequence: Int,
    color: Color,
    onLevelChange: (Int) -> Unit
) {
    Icon(
        Icons.Default.Star,
        contentDescription = null,
        tint = color,
        modifier = Modifier
            .clickable { onLevelChange(sequence) }
            .testTag(SLEEP_QUALITY)
    )
}

@Composable
private fun Input(
    label: String,
    unit: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    testTag: String
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )


        OutlinedTextField(
            value = value.asTextFieldValue(),
            onValueChange = { onValueChange(it.text) },
            label = { Text(unit) },
            modifier = Modifier
                .weight(1f)
                .testTag(testTag),
            singleLine = true,
            keyboardOptions = keyboardOptions
        )
    }

}




@Composable
private fun ChoiceItem(
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


        val color = when(option) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardioType() {

    val cardioTypes = Cardio.entries
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.testTag(CARDIO_DROP_DOWN)
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text("Cardio") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            cardioTypes.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name) },
                    onClick = {
                        selectedOption = option.name
                        expanded = false
                    },
                    modifier = Modifier.testTag(CARDIO_TYPE_DROP_DOWN_ITEM + option.name)
                )
            }
        }
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
                    gymNotes = "WoW",
                    proteinGrams = "140",
                    sleepQuality = "3",
                    performedWorkout = false,
                    hadCreatine = false,
                    hadCheatMeal = true,
                    musclesTrained = listOf(),
                    litersOfWater = "2.5",
                    cardioMinutes = "30",
                    meal = "Burger",
                    date = Date(),
                    cardio = Cardio.Bicycle.name
                )
            ),
            navigateBack = {},
            onUpdateTextField = { _, _ -> },
            onUpdateCheckField = { _, _ -> },
            navigateToWorkout = {},
            onSelectMuscle = {},
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