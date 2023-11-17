package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.onBackgroundColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.secondaryColor

@Composable
fun AppRadioButton(
    radioOptions: List<RadioButtonInfo>,
    selectedOption: String,
    onOptionSelected: (RadioButtonInfo) -> Unit,
    isIcon: Boolean = false,
) {
    Row(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            if (text.enable)
                Row(
                    Modifier
                        .selectable(
                            selected = (text.text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text.text == selectedOption),
                        onClick = null // null recommended for accessibility with screen readers
                    )

                    if (isIcon)
                        DrawableImage(
                            id = text.icon!!,
                            modifier = Modifier
                                .size(70.dp)
                                .padding(start = 16.dp),
                            colorFilter = ColorFilter.tint(
                                color =
                                if (text.text == selectedOption)
                                    primaryColor.invoke()
                                else
                                    onBackgroundColor.invoke()
                            )
                        )
                    else
                        Text(
                            text = text.text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                }
        }
    }
}

data class RadioButtonInfo(
    val id: Int,
    val text: String,
    val isIcon: Boolean = false,
    val enable: Boolean = true,
    val icon: Int? = null
)