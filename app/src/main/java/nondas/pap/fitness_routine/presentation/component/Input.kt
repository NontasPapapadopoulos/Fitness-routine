package nondas.pap.fitness_routine.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import nondas.pap.fitness_routine.presentation.util.asTextFieldValue

@Composable
fun Input(
    label: String,
    unit: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    testTag: String,
    modifier: Modifier = Modifier
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )


        OutlinedTextField(
            value = value.asTextFieldValue(),
            onValueChange = { if (it.text.isDigitsOnly()) onValueChange(it.text) },
            label = { Text(unit) },
            modifier = Modifier
                .weight(1f)
                .testTag(testTag),
            singleLine = true,
            keyboardOptions = keyboardOptions
        )

}
}