package me.taste2plate.app.customer.presentation.screens.auth.signup

import android.text.Html
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.emailString
import me.taste2plate.app.customer.presentation.utils.mobileNumber
import me.taste2plate.app.customer.presentation.utils.referralCodeString
import me.taste2plate.app.customer.presentation.utils.signUpString
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppCheckBox
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun SignUpScreen(
    onNavigateToHomeScreen: () -> Unit
) {
    var mobile by remember {
        mutableStateOf("")
    }
    var emailValue by remember {
        mutableStateOf("")
    }
    var referralCode by remember {
        mutableStateOf("")
    }
    var termsAccept by remember {
        mutableStateOf(false)
    }
    AppScaffold {
        Column {
            AppTextField(
                value = emailValue,
                onValueChanged = { emailValue = it },
                hint = emailString,
                leadingIcon = {
                    MaterialIcon(
                        imageVector = Icons.Outlined.Email
                    )
                }
            )
            VerticalSpace(space = SpaceBetweenViews)
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
            AppTextField(
                value = referralCode,
                onValueChanged = { referralCode = it },
                hint = referralCodeString,
                leadingIcon = {
                    MaterialIcon(
                        imageVector = Icons.Outlined.AccountBox
                    )
                }
            )
            VerticalSpace(space = SpaceBetweenViews)
            val text = Html.fromHtml(
                "By signing up, you agree to our <span style=\"color:#de2228\">Terms</span> and <span style=\"color:#de2228\">Conditions</span>".trim(),
                0
            )
            AppCheckBox(
                checked = termsAccept,
                onCheckedChange = {
                    termsAccept = !termsAccept
                },
                text = text.toString()
            )
            VerticalSpace(space = SpaceBetweenViews)
            AppButton(
                text = signUpString
            ) {
                onNavigateToHomeScreen()
            }
        }
    }
}

@Preview
@Composable
fun SignInPreview() {
    T2PCustomerAppTheme {
        SignUpScreen {}
    }
}