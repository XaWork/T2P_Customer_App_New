package me.taste2plate.app.customer.presentation.screens.auth.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.auth.AuthEvents
import me.taste2plate.app.customer.presentation.screens.auth.AuthViewModel
import me.taste2plate.app.customer.presentation.screens.home.widgets.HeadingChipWithLine
import me.taste2plate.app.customer.presentation.theme.HighSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.getOtpString
import me.taste2plate.app.customer.presentation.utils.mobileNumber
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun OnBoardingScreen(
    viewModel: AuthViewModel,
    onNavigateToOtpScreen: () -> Unit,
) {

    val state = viewModel.state
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = state) {
        when {
            state.loginModel != null -> {
                scope.launch {
                    onNavigateToOtpScreen()
                }
            }
        }
    }

    AppScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
            ) {

                DrawableImage(
                    id = R.drawable.logo_new,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            VerticalSpace(space = SpaceBetweenViews)

            Column(
                modifier = Modifier.padding(ScreenPadding)
            ) {
                Text(
                    text = "India’s Most Trusted Intercity Food Delivery Application",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                VerticalSpace(space = HighSpacing)

                HeadingChipWithLine(
                    text = "Login/Signup",
                    textColor = Color.Unspecified
                )

                VerticalSpace(space = SpaceBetweenViews)

                AppTextField(
                    value = viewModel.mobile,
                    onValueChanged = {
                        if (it.length <= viewModel.mobileLength) viewModel.mobile = it
                    },
                    hint = mobileNumber,
                    keyboardType = KeyboardType.Phone,
                    isError = state.isError,
                    errorMessage = if (state.isError) state.message!!
                    else "Limit: ${viewModel.mobile.length}/${viewModel.mobileLength}",
                    leadingIcon = {
                        MaterialIcon(
                            imageVector = Icons.Outlined.Phone
                        )
                    })

                VerticalSpace(space = SpaceBetweenViews)

                if (state.loading)
                    ShowLoading()
                else
                    AppButton(
                        text = getOtpString,
                    ) {
                        viewModel.onEvent(AuthEvents.Login)
                    }

                VerticalSpace(space = SpaceBetweenViews)

                Text(
                    text = "By continuing, you agree to our terms and conditions",
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    T2PCustomerAppTheme {
        //OnBoardingScreen {}
    }
}