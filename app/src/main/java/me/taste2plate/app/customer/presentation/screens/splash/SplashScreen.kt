package me.taste2plate.app.customer.presentation.screens.splash

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.permissions.RequestPermissions
import me.taste2plate.app.customer.presentation.widgets.AppScaffold

@Composable
fun SplashScreen(
    onNavigateToOnBoardingScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state = viewModel.state

    /*val appSignatureHelper = AppSignatureHelper(context)
    Log.e("Atiar OTP Hashkey: ", "key: ${appSignatureHelper.appSignatures}")*/
    //VzqMQBwK7m1

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        RequestPermissions(permission = listOf(
            Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.RECEIVE_SMS
        ), permissionStatus = {})
    else
        RequestPermissions(permission = listOf(
            Manifest.permission.RECEIVE_SMS
        ), permissionStatus = {})


    LaunchedEffect(state) {
        if (!state.loading) {
            if (state.isLogin && state.user != null) {
                onNavigateToHomeScreen()
            } else{
                onNavigateToOnBoardingScreen()
            }
        }
    }

    AppScaffold(
        paddingValues = PaddingValues(0.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Preview(name = "Splash Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashPreviewDark() {
    //SplashScreen({}, {}, {})
}