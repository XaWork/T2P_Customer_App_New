package me.taste2plate.app.customer.presentation.screens.auth.onboarding

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.Identity
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
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToOtpScreen: () -> Unit,
) {

    val state = viewModel.state
    val context = LocalContext.current
    val activity = context as Activity
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = state) {
        when {
            state.loginModel != null && !state.isError -> {
                onNavigateToOtpScreen()
            }
        }
    }


    //show phone number dialog
    val TAG = "On boarding"
    val phoneNumberHintIntentResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                val phoneNumber = Identity.getSignInClient(activity).getPhoneNumberFromIntent(result.data)
                Log.e(TAG, "Phone Number is $phoneNumber")
                viewModel.mobile = phoneNumber.takeLast(10)
            } catch(e: Exception) {
                Log.e(TAG, "Phone Number Hint failed")
            }
        }
/*
    LaunchedEffect(key1 = Unit){
        val request: GetPhoneNumberHintIntentRequest = GetPhoneNumberHintIntentRequest.builder().build()

        Identity.getSignInClient(activity)
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener { result: PendingIntent ->
                try {
                    phoneNumberHintIntentResultLauncher.launch(
                        IntentSenderRequest.Builder(result).build()
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Launching the PendingIntent failed")
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "Phone Number Hint failed")
            }
    }*/

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
                    imeAction = ImeAction.Done,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            viewModel.onEvent(AuthEvents.Login)
                        }
                    ),
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
                        keyboardController?.hide()
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