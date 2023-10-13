package me.taste2plate.app.customer.presentation.screens.auth.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.utils.getOtpString
import me.taste2plate.app.customer.presentation.utils.mobileNumber
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun SignInScreen(
    onNavigateToOTPScreen: () -> Unit
) {
    var mobile by remember {
        mutableStateOf("")
    }
    AppScaffold {
        Column {
            AppTextField(
                value = mobile,
                onValueChanged = { mobile = it },
                hint = mobileNumber,
                leadingIcon = {
                    MaterialIcon(
                        imageVector = Icons.Outlined.Phone
                    )
                }
            )
            VerticalSpace(space = SpaceBetweenViews)
            AppButton(
                text = getOtpString
            ) {
                onNavigateToOTPScreen()
            }
        }
    }
}

@Preview
@Composable
fun SignInPreview() {
    SignInScreen() {}
}