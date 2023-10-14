package me.taste2plate.app.customer.presentation.screens.auth.otp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.auth.signin.SignInScreen
import me.taste2plate.app.customer.presentation.theme.ExtraHighSpacing
import me.taste2plate.app.customer.presentation.theme.HighSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.otpString
import me.taste2plate.app.customer.presentation.utils.verifyOtpString
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.OtpTextField
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun OTPScreen(
    onNavigateToHomeScreen: () -> Unit
) {
    var otp by remember {
        mutableStateOf("")
    }
    AppScaffold {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = ScreenPadding)
        ) {
            VerticalSpace(space = ExtraHighSpacing)
            DrawableImage(id = R.drawable.logo_new, modifier = Modifier.size(100.dp))
            VerticalSpace(space = HighSpacing)
            Text("Enter OTP", textAlign = TextAlign.Start)
            VerticalSpace(space = SpaceBetweenViews)
            OtpTextField(
                otpText = otp,
                onOtpTextChange = { value, _ ->
                    otp = value
                }
            )
            VerticalSpace(space = SpaceBetweenViews)
            AppButton(
                text = verifyOtpString
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
        OTPScreen {}
    }
}