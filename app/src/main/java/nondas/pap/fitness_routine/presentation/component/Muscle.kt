package nondas.pap.fitness_routine.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import nondas.pap.fitness_routine.domain.entity.enums.Muscle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MusclesTrained(
    onSelectMuscle: (Muscle) -> Unit,
    selectedMuscles: List<Muscle>,
    testTag: String
) {


    FlowRow {
        Muscle.entries.forEach {
            Muscle(
                muscle = it,
                isSelected = selectedMuscles.contains(it),
                select = { onSelectMuscle(it) },
                testTag = testTag
            )
        }
    }


}

@Composable
private fun Muscle(
    muscle: Muscle,
    isSelected: Boolean,
    select: () -> Unit,
    testTag: String
) {

    val color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    Text(
        text = muscle.name,
        modifier = Modifier
            .padding(3.dp)
            .clickable { select() }
            .border(BorderStroke(1.dp, color = color), shape = CircleShape)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .testTag(testTag + muscle.name)

    )

}
