package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.screens.countries
import me.taste2plate.app.customer.presentation.theme.GreyDark
import me.taste2plate.app.customer.presentation.theme.GreyLight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    hint: String = "",
    readOnly : Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = "",
        onValueChange = { onValueChanged(it) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        readOnly = readOnly,
        label = { Text(
            text = hint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        ) },
    )
}


@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> "0"
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(40.dp)
            .border(
                1.dp, color = when {
                    isFocused -> GreyDark
                    else -> GreyLight
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
        color = if (isFocused) Color.White else Color.Black
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDropDown(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onExpandedChange: () -> Unit,
    selectedText: String,
    hint: String ="",
    onTextChanged: (String) -> Unit

) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            onExpandedChange()
        }
    ) {
        AppTextField(
            modifier = Modifier.menuAnchor(),
            value = selectedText,
            onValueChanged = {},
            readOnly = true,
            hint = hint,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange() }
        ) {
            countries.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        onTextChanged(item)
                        onExpandedChange()
                    }
                )
            }
        }
    }
}