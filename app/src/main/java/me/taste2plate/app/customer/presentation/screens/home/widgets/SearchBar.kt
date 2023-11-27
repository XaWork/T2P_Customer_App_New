package me.taste2plate.app.customer.presentation.screens.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon

@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier
            .padding(ScreenPadding)
            .background(screenBackgroundColor.invoke())
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = screenBackgroundColor.invoke(),
            unfocusedContainerColor = screenBackgroundColor.invoke(),
            disabledContainerColor = screenBackgroundColor.invoke(),
        ),
        value = value,
        onValueChange = onValueChange,
        shape = CircleShape,
        /*label = {
            Text("Search")
        },*/
        leadingIcon = {
            MaterialIcon(imageVector = Icons.Outlined.Search)
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        )
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    T2PCustomerAppTheme {
       // AppSearchBar("", {}, {})
    }
}