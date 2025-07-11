package nondas.pap.fitness_routine.presentation.ui.screen.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.entity.NoteDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Cardio
import nondas.pap.fitness_routine.domain.entity.enums.Choice
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import nondas.pap.fitness_routine.presentation.component.MusclesTrained
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CARDIO_DROP_DOWN
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CARDIO_TEXT_FIELD
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CARDIO_TYPE_DROP_DOWN_ITEM
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.GYM_NOTES_TEXT_FIELD
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.MUSCLE_ITEM
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.WORKOUT_CHECK_BOX
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.WORKOUT_CONTENT
import nondas.pap.fitness_routine.presentation.ui.theme.AppTheme
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing1
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing2
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing4
import nondas.pap.fitness_routine.presentation.util.asTextFieldValue
import nondas.pap.fitness_routine.presentation.util.getIcon
import java.util.Date
import java.util.UUID

@Composable
fun WorkoutTab(
    dailyReport: DailyReportDomainEntity,
    notes: List<NoteDomainEntity>,
    cardios: List<CardioDomainEntity>,
    onUpdateCheckField: (Boolean, CheckBoxField) -> Unit,
    onSelectMuscle: (Muscle) -> Unit,
    onAddCardio: () -> Unit,
    onDeleteCardio: (CardioDomainEntity) -> Unit,
    onUpdateCardio: (CardioDomainEntity, CardioField, String) -> Unit,
    onAddNote: () -> Unit,
    onUpdateNote: (NoteDomainEntity, String) -> Unit,
    onDeleteNote: (NoteDomainEntity) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .testTag(WORKOUT_CONTENT)
    ) {
        Spacer(modifier = Modifier.height(contentSpacing2))

        ChoiceCheckBoxItem(
            option = Choice.Workout,
            isChecked = dailyReport.performedWorkout,
            onCheckedChange = { onUpdateCheckField(it, CheckBoxField.Workout) },
            testTag = WORKOUT_CHECK_BOX,
            icon = Choice.Workout.getIcon()
        )

        Spacer(modifier = Modifier.height(contentSpacing2))


        cardios.forEachIndexed { index, cardio ->
            CardioItem(
                cardio = cardio,
                onUpdateCardio = { cardio, field, value -> onUpdateCardio(cardio, field, value) },
                testTag = CARDIO_TEXT_FIELD + index,
                addCardio = onAddCardio,
                deleteCardio = { onDeleteCardio(it) } ,
            )
        }


        Spacer(modifier = Modifier.height(contentSpacing2))

        MusclesTrained(
            selectedMuscles = dailyReport.musclesTrained,
            onSelectMuscle = onSelectMuscle,
            testTag = MUSCLE_ITEM
        )

        Spacer(modifier = Modifier.height(contentSpacing4))


        notes.forEach {
            NoteItem(
                note = it,
                onUpdateNote = { note, value -> onUpdateNote(note, value) },
                onAddNote = onAddNote,
                onDeleteNote = { if (notes.size > 1) onDeleteNote(it) }
            )
        }

    }

}



@Composable
private fun CardioItem(
    cardio: CardioDomainEntity,
    onUpdateCardio: (CardioDomainEntity, CardioField, String) -> Unit,
    addCardio: () -> Unit,
    deleteCardio: (CardioDomainEntity) -> Unit,
    testTag: String,
) {

    Row(
//        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CardioType(
            selectedCardio = cardio.type,
            onUpdateCardio = { onUpdateCardio(cardio, CardioField.Type, it) },
            modifier = Modifier.weight(0.65f)
        )

        Spacer(modifier = Modifier.width(contentSpacing4))

        OutlinedTextField(
            value = cardio.minutes.asTextFieldValue(),
            onValueChange = {
                if (it.text.isDigitsOnly())
                    onUpdateCardio(cardio, CardioField.Minutes, it.text)
            },
            label = { Text("min") },
            modifier = Modifier
                .weight(0.25f)
                .testTag(testTag),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Row(modifier = Modifier.weight(0.3f)) {
            IconButton(onClick = addCardio) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

            IconButton(onClick = { deleteCardio(cardio) } ) {
                Icon(
                    imageVector = Icons.Default.RemoveCircleOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardioType(
    selectedCardio: String,
    onUpdateCardio: (String) -> Unit,
    modifier: Modifier
) {

    val cardioTypes = Cardio.entries
    var expanded by remember { mutableStateOf(false) }

//    var selectedOption by remember {
//        mutableStateOf(
//            if (selectedCardio.isEmpty()) Cardio.Walking.name
//            else Cardio.valueOf(selectedCardio).name
//        )
//    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.testTag(CARDIO_DROP_DOWN)
    ) {
        OutlinedTextField(
            value = selectedCardio,
            onValueChange = {},
            readOnly = true,
            label = { Text("Cardio") },
            modifier = modifier
                .menuAnchor(),
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
                        onUpdateCardio(option.name)
//                        onUpdateTextField(option.name, Field.Cardio)
                        expanded = false
                    },
                    modifier = Modifier.testTag(CARDIO_TYPE_DROP_DOWN_ITEM + option.name)

                )
            }
        }
    }

}



@Composable
private fun NoteItem(
    note: NoteDomainEntity,
    onAddNote: () -> Unit,
    onUpdateNote: (NoteDomainEntity, String) -> Unit,
    onDeleteNote: (NoteDomainEntity) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(vertical = contentSpacing1)
            .fillMaxWidth()
    ) {

        OutlinedTextField(
            value = note.text.asTextFieldValue(),
            label = { Text(text = "Gym Notes") },
            onValueChange = { onUpdateNote(note, it.text) },
            singleLine = true,
            modifier = Modifier
                .weight(0.7f)
                .testTag(GYM_NOTES_TEXT_FIELD)
        )


        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.weight(0.3f)
        ) {
            IconButton(onClick = onAddNote) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

            IconButton(onClick = { onDeleteNote(note) } ) {
                Icon(
                    imageVector = Icons.Default.RemoveCircleOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }

    }

}


@Preview
@Composable
private fun WorkoutTabPreview() {
    AppTheme {
        WorkoutTab(
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
            notes = generateNotes(),
            cardios = listOf(
                CardioDomainEntity(
                    id = UUID.randomUUID().toString(),
                    date = Date(),
                    type = Cardio.Walking.name,
                    minutes = "60"
                ),
            ),
            onUpdateCheckField = { _, _ -> },
            onSelectMuscle = {},
            onAddCardio = { },
            onDeleteCardio = {},
            onUpdateCardio = { _, _, _ ->},
            onAddNote = { },
            onUpdateNote = {_, _->},
            onDeleteNote = {}
        )
    }
}