package com.example.fitness_routine.presentation.util

import android.graphics.drawable.VectorDrawable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.example.fitness_routine.R
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.ui.icons.FitnessDiary
import com.example.fitness_routine.presentation.ui.icons.myiconpack.FitnessTracker24px
import kotlinx.coroutines.selects.select
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Long.toDate(): Date = this.let { Date(it) }

fun Date.toTimeStamp(): Long = this.time


fun Long.toFormattedDate(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(date)
}


fun Date.toFormattedDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(this)
}

//fun Date.format(): Date {
//    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//    val formattedDateString = formatter.format(this)
//    return formatter.parse(formattedDateString)!!
//}


fun String.toList(): List<String> = this.split(",").map { it.trim() }

fun List<String>.convertToString(): String = this.joinToString(separator = ",")


fun String.capitalize(): String = this.lowercase().replaceFirstChar { it.uppercase() }


fun List<String>.toMuscles(): List<Muscle> = if (this.isEmpty()) listOf() else this.map { Muscle.valueOf(it) }


fun Choice.getIcon(): ImageVector =
    when(this) {
        Choice.Workout -> Icons.Default.FitnessCenter
        Choice.Creatine -> Icons.Default.ElectricBolt
        Choice.Cheat -> Icons.Default.Fastfood
    }


@Composable
fun Choice.getColor(): Color =
    when(this) {
        Choice.Workout -> MaterialTheme.colorScheme.primary
        Choice.Creatine -> colorResource(R.color.creatine)
        Choice.Cheat -> colorResource(R.color.cheat_meal)
    }



fun String.asTextFieldValue() = TextFieldValue(this, selection = TextRange(this.length))

fun String.asTextFieldValue(cursorPosition: Int): TextFieldValue {
    return if (cursorPosition == selectAllPosition) {
        TextFieldValue(this, selection = TextRange(0, this.length))
    } else {
        val position = this.boundedPosition(cursorPosition)
        TextFieldValue(this, selection = TextRange(position))
    }
}

fun String.boundedPosition(position: Int): Int {
    return position.coerceIn(minimumValue = 0, maximumValue = (length).coerceAtLeast(0))
}

const val selectAllPosition = -1

fun String.updateCursor(cursor: Int, newValue: String): Int {
    return if (cursor == selectAllPosition && this != newValue) {
        newValue.length
    } else {
        val cursorChange = (newValue.length - this.length)
        (cursor + cursorChange).coerceIn(0, newValue.length)
    }
}

fun String.moveCursorToEnd(): Int = this.length