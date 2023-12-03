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
import androidx.compose.ui.unit.TextUnit
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
    fontSize: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Unspecified
) {
    Row(Modifier.selectableGroup()) {
        radioOptions.forEach { radioButtonInfo ->
            Row(
                Modifier
                    .selectable(
                        selected = (radioButtonInfo.text == selectedOption),
                        onClick = { if (radioButtonInfo.enable) onOptionSelected(radioButtonInfo) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = if (radioButtonInfo.enable) (radioButtonInfo.text == selectedOption) else false,
                    onClick = null,
                    enabled = radioButtonInfo.enable,
                )

                if (isIcon)
                    DrawableImage(
                        id = radioButtonInfo.icon!!,
                        modifier = Modifier
                            .size(70.dp)
                            .padding(start = 16.dp),
                        colorFilter = ColorFilter.tint(
                            color =
                            if (radioButtonInfo.text == selectedOption)
                                primaryColor.invoke()
                            else
                                onBackgroundColor.invoke()
                        )
                    )
                else
                    Text(
                        text = radioButtonInfo.text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp), fontSize = fontSize,
                        color = if (radioButtonInfo.enable) color else Color.Gray
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