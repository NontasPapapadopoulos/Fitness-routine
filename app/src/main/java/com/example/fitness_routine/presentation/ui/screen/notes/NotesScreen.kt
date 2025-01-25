package com.example.fitness_routine.presentation.ui.screen.notes

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.domain.entity.NoteDomainEntity
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing2
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4
import com.example.fitness_routine.presentation.util.capitalize
import com.example.fitness_routine.presentation.util.toFormattedDate
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.util.Date


@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    navigateBack: () -> Unit
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
        is NotesState.Content -> {
            NotesContent(
                content = state,
                navigateBack = navigateBack
            )
        }
        NotesState.Idle -> {
            LoadingBox()
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotesContent(
    content: NotesState.Content,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Notes")
                },
                navigationIcon = { BackButton(navigateBack) }
            )
        },

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(contentSpacing4)
                .padding(it),

        ) {

            NotesContainer(
                content.notes,
            )

        }
    }
}


@Composable
private fun NotesContainer(
    sessions: List<NoteDomainEntity>,
) {

    val monthGroups = groupByMonth(sessions)

    monthGroups.onEachIndexed { _, entry ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(contentSpacing2)
                )
                .padding(contentSpacing4)

        ) {
            MonthName(entry)

            Spacer(modifier = Modifier.height(contentSpacing4))

            val days = groupByDate(entry)


            days.onEachIndexed { index, day ->
                DailyNotes(notes = day.value)
                if (index < days.size - 1 ) {
                    Spacer(modifier = Modifier.height(contentSpacing2))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(contentSpacing4))
                }
            }
        }
        Spacer(modifier = Modifier.height(contentSpacing2))
    }
}

@Composable
private fun MonthName(entry: Map.Entry<Month, List<NoteDomainEntity>>) {
    Text(text = entry.key.name.capitalize())
}

@Composable
private fun groupByMonth(sessions: List<NoteDomainEntity>) =
    sessions
        .groupBy {
            val localDate = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            localDate.month
        }

@Composable
private fun groupByDate(entry: Map.Entry<Month, List<NoteDomainEntity>>) =
    entry.value.groupBy {
        val localDate = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        localDate.dayOfYear
    }

@Composable
private fun DailyNotes(
    notes: List<NoteDomainEntity>
) {
    Text(text = notes.first().date.toFormattedDate())

    notes.forEach {
            Text(
                text = "• ${it.text}",
            )
}

}


@Composable
@Preview
private fun NotesContentPreview() {
    AppTheme {
        NotesContent(
            content = NotesState.Content(
                notes = generateNotes()
            ),
            navigateBack = {}
        )
    }

}

private fun generateNotes(): List<NoteDomainEntity> {
    return (10 downTo 1).map {
        val localDate = LocalDate.of(2024, if (it < 5) 1 else 2, it)
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        NoteDomainEntity(
            date = date,
            text = "Note $it",
            id = "",
            userId = ""
        )
    }.plus((10 downTo 1).map {
        val localDate = LocalDate.of(2024, if (it < 5) 1 else 2, it)
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        NoteDomainEntity(
            date = date,
            text = "Note $it",
            id = "",
            userId = ""
        )

    })
}

