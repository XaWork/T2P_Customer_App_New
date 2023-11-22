package me.taste2plate.app.customer.presentation.screens.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.auth.AuthEvents
import me.taste2plate.app.customer.presentation.screens.auth.AuthViewModel
import me.taste2plate.app.customer.presentation.theme.HighSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.emailString
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.showToast

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToAddEditAddressScreen: () -> Unit
) {

    val state = viewModel.state
    if (state.isError && state.message != null) {
        showToast(state.message)
        viewModel.onEvent(AuthEvents.UpdateState)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(AuthEvents.GetUser)
    }

    LaunchedEffect(state) {
        if (state.registerSuccess)
            onNavigateToAddEditAddressScreen()
    }

    AppScaffold {
        Column(
            modifier = Modifier
                .padding(horizontal = ScreenPadding)
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
                value = viewModel.fullName,
                onValueChanged = { viewModel.fullName = it },
                hint = "Full Name",
                leadingIcon = {
                    MaterialIcon(
                        imageVector = Icons.Outlined.Person
                    )
                }
            )

            VerticalSpace(space = SpaceBetweenViews)

            AppTextField(
                value = viewModel.email,
                onValueChanged = { viewModel.email = it },
                hint = emailString,
                leadingIcon = {
                    MaterialIcon(
                        imageVector = Icons.Outlined.Email
                    )
                })

            VerticalSpace(space = SpaceBetweenViews)

            if (state.loading)
                ShowLoading()
            else
                AppButton(
                    text = "Continue"
                ) {
                    viewModel.onEvent(AuthEvents.SignUp)
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