package me.taste2plate.app.customer.presentation.screens.address

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.screens.countries
import me.taste2plate.app.customer.presentation.theme.LowSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppDropDown
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun AddEditAddressScreen() {
    AppScaffold(
        topBar = {
            AppTopBar {}
        }
    ) {
        ContentAddEditAddressScreen()
    }
}

@Composable
fun ContentAddEditAddressScreen() {
    var fullName by remember {
        mutableStateOf("")
    }

    var expanded by remember { mutableStateOf(false) }
    var pinCodeExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(countries[0]) }
    var pinCode by remember { mutableStateOf(countries[0]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(ScreenPadding)
    ) {
        Column {
            AppTextField(
                value = fullName, onValueChanged = {
                    fullName = it
                },
                hint = "Full name"
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)


            AppTextField(
                value = fullName, onValueChanged = {
                    fullName = it
                },
                hint = "Phone"
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            AppTextField(
                value = fullName, onValueChanged = {
                    fullName = it
                },
                hint = "House No./Building Name"
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            AppTextField(
                value = fullName, onValueChanged = {
                    fullName = it
                },
                hint = "Road no/Area/Colony"
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            AppDropDown(
                expanded,
                hint = "Country",
                onExpandedChange = {
                    expanded = !expanded
                },
                selectedText = selectedText,
                onTextChanged = {
                    selectedText = it
                }
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            AppDropDown(
                expanded,
                hint = "State",
                onExpandedChange = {
                    expanded = !expanded
                },
                selectedText = selectedText,
                onTextChanged = {
                    selectedText = it
                }
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            AppDropDown(
                expanded,
                hint = "City",
                onExpandedChange = {
                    expanded = !expanded
                },
                selectedText = selectedText,
                onTextChanged = {
                    selectedText = it
                }
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            SpaceBetweenRow(item1 = {
                AppDropDown(
                    pinCodeExpanded,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = LowSpacing)
                        .weight(1f),
                    onExpandedChange = {
                        pinCodeExpanded = it
                    },
                    hint = "PinCode",
                    selectedText = pinCode,
                    onTextChanged = {
                        pinCode = it
                    }
                )
            }) {
                AppTextField(
                    modifier = Modifier
                        .padding(start = LowSpacing)
                        .weight(1f),
                    value = fullName, onValueChanged = {
                        fullName = it
                    },
                    hint = "Landmark(Optional)"
                )
            }

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            val radioOptions = listOf("Home", "Work", "Other")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
            Row(Modifier.selectableGroup()) {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .height(56.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }

        AppButton(
            text = "Add",
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {}
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddEditAddressScreenPreview() {
    T2PCustomerAppTheme {
        AddEditAddressScreen()
    }
}