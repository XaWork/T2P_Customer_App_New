package me.taste2plate.app.customer.presentation.screens.auth.otp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import me.taste2plate.app.customer.presentation.screens.auth.AuthEvents
import me.taste2plate.app.customer.presentation.screens.auth.AuthViewModel
import me.taste2plate.app.customer.presentation.theme.HighSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.outlineColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.verifyOtpString
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.OtpTextField
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun OTPScreen(
    viewModel: AuthViewModel,
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToSignUPScreen: () -> Unit,
) {

    val state = viewModel.state
    var seconds by remember { mutableIntStateOf(59) }

    //Observe State
    when {
        state.loginSuccess && state.verifyOTPModel != null -> {
            if (state.loginModel!!.newUser)
                onNavigateToHomeScreen()
            else
                onNavigateToSignUPScreen()
        }
    }

    if (seconds > 0) {
        LaunchedEffect(Unit) {
            while (seconds > 0) {
                delay(1000)
                seconds--
            }
        }
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "OTP Verification",
            ) {}
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = ScreenPadding)
                .fillMaxSize()
        ) {
            val mobileString = buildAnnotatedString {
                withStyle(SpanStyle()) {
                    append("We have send a verification code to")
                }

                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("\n+91 ${viewModel.mobile}")
                }
            }

            Text(mobileString, textAlign = TextAlign.Center)

            VerticalSpace(space = HighSpacing)

            OtpTextField(
                otpText = viewModel.otp,
                onOtpTextChange = { value, _ ->
                    viewModel.otp = value
                }
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            if (state.isError)
                Text(
                    state.message!!,
                    textAlign = TextAlign.Center,
                    color =
                    primaryColor.invoke()
                )

            VerticalSpace(space = HighSpacing)

            if (state.loading)
                ShowLoading()
            else
                AppButton(
                    text = verifyOtpString
                ) {
                    viewModel.onEvent(AuthEvents.VerifyOTP)
                }

            val resendOTP = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Didn't get the OTP? ")
                }

                if (seconds == 0) {
                    withStyle(
                        SpanStyle(
                            color = primaryColor.invoke(),
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Resend OTP")
                    }
                } else
                    withStyle(
                        SpanStyle(
                            color = outlineColor.invoke()
                        )
                    ) {
                        append("Resend OTP in ${seconds}s")
                    }
            }

            VerticalSpace(space = SpaceBetweenViews)

            Text(resendOTP, textAlign = TextAlign.Start, modifier = Modifier
                .clickable {
                    if (seconds == 0)
                        viewModel.onEvent(AuthEvents.ResendOTP)
                })
        }
    }
}

@Preview
@Composable
fun SignInPreview() {
    T2PCustomerAppTheme {
        // OTPScreen({}, {})
    }
}