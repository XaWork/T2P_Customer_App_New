package me.taste2plate.app.customer.presentation.screens.auth.otp

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.presentation.screens.auth.AuthEvents
import me.taste2plate.app.customer.presentation.screens.auth.AuthViewModel
import me.taste2plate.app.customer.presentation.theme.HighSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.outlineColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.utils.verifyOtpString
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.OtpTextField
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

/***
 * auto fetch otp vlog : https://www.linkedin.com/pulse/android-automatic-otp-retrieval-jetpackcompose-also-work-talukdar/
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OTPScreen(
    viewModel: AuthViewModel,
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToSignUPScreen: () -> Unit,
) {

    val state = viewModel.state
    val context = LocalContext.current
    var seconds by remember { mutableIntStateOf(59) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember {
        FocusRequester()
    }


    LaunchedEffect(state) {
        if(state.loginSuccess)
            onNavigateToHomeScreen()
    }

   /* val verificationCode = remember { mutableStateOf("") }
    LaunchedEffect(1) {
        val myOTPReceiver = OTPReceiver()

        //Registering Broadcast receiver here instead of manifest.
        context.registerReceiver(
            myOTPReceiver,
            IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION),
            Context.RECEIVER_NOT_EXPORTED
        )

        //Receiving the OTP
        myOTPReceiver.init(object : OTPReceiver.OTPReceiveListener {
            override fun onOTPReceived(otp: String?) {
                Log.e("OTP ", "OTP Received  ")
                otp?.let { verificationCode.value = it }

                // when its true automatically run the function which
                // supposed to be run by clicking verify button
                // verifyNumberOnClick.value = true

                context.unregisterReceiver(myOTPReceiver)
            }

            override fun onOTPTimeOut() {
                Log.e("OTP ", "Timeout")
            }
        })
    }*/

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
                .onGloballyPositioned {
                    coroutineScope.launch {
                        keyboardController?.show()
                        focusRequester.requestFocus()
                    }
                }
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
                modifier = Modifier.focusRequester(focusRequester),
                otpText = viewModel.otp,
                onDone = {
                    keyboardController?.hide()
                    viewModel.onEvent(AuthEvents.VerifyOTP)
                },
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
                .noRippleClickable {
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