package me.taste2plate.app.customer.presentation.screens.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.auth.AuthViewModel
import me.taste2plate.app.customer.presentation.theme.HighSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.emailString
import me.taste2plate.app.customer.presentation.utils.referralCodeString
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
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
        Column(
            modifier = Modifier.padding(horizontal = ScreenPadding)
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            VerticalSpace(space = HighSpacing)

            DrawableImage(
                id = R.drawable.logo_new,
                //modifier = Modifier.size(100.dp)
            )

            VerticalSpace(space = SpaceBetweenViews)

            AppTextField(
                value = emailValue,
                onValueChanged = { emailValue = it },
                hint = "Full Name",
                leadingIcon ={
                    MaterialIcon(
                        imageVector = Icons.Outlined.Person
                    )
                }
            )

            VerticalSpace(space = SpaceBetweenViews)

            AppTextField(
                value = emailValue,
                onValueChanged = { emailValue = it },
                hint = emailString,
                leadingIcon =  {
                    MaterialIcon(
                        imageVector = Icons.Outlined.Email
                    )
                })

            VerticalSpace(space = SpaceBetweenViews)

            AppTextField(
                value = referralCode,
                onValueChanged = { referralCode = it },
                hint = referralCodeString,
                leadingIcon ={
                    MaterialIcon(
                        imageVector = Icons.Outlined.AccountBox
                    )
                }
            )

            VerticalSpace(space = SpaceBetweenViews)

            AppButton(
                text = "Continue"
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
        //SignUpScreen {}
    }
}