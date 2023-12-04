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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.permissions.RequestPermissions
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.utils.AppSignatureHelper

@Composable
fun SplashScreen(
    onNavigateToOnBoardingScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToAddEditAddressScreenScreen: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    var appUpToDate by remember {
        mutableStateOf(true)
    }

    val appSignatureHelper = AppSignatureHelper(context)
    Log.e("Atiar OTP Hashkey: ", "key: ${appSignatureHelper.appSignatures}")
    //VzqMQBwK7m1

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        RequestPermissions(permission = listOf(
            Manifest.permission.POST_NOTIFICATIONS
        ), permissionStatus = {})

    LaunchedEffect(state) {
        if (!state.loading) {
            if (state.isLogin && state.user != null) {
                if (!state.user.email.isNullOrEmpty())
                    if (state.addressListModel != null && state.addressListModel.result.isNotEmpty())
                        onNavigateToHomeScreen()
                    else
                        onNavigateToAddEditAddressScreenScreen()
                else
                    onNavigateToSignUpScreen()
            } else
                onNavigateToOnBoardingScreen()
        }
    }

    /*  LaunchedEffect(key1 = Unit) {
          if (!state.loading && state.isLogin && state.user != null) {
              if (!state.user.email.isNullOrEmpty())
                  if (state.addressListModel != null && state.addressListModel.result.isNotEmpty())
                      onNavigateToHomeScreen()
                  else
                      onNavigateToAddEditAddressScreenScreen()
              else
                  onNavigateToSignUpScreen()
          } else
              onNavigateToOnBoardingScreen()
      }*/

    if (appUpToDate)
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
    else
        UpdateAppScreen {
            moveToPlayStore(context)
        }
}

private fun appUpToDate(context: Context, state: SplashState): Boolean {
    val version = state.settings!!.result.customerAndroidVersion
    val packageManager = context.packageManager
    val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
    Log.e(
        "version",
        "Current App Version : ${packageInfo.versionName}\nStored App version : $version"
    )
    return true
}

private fun moveToPlayStore(context: Context) {
    val appPackageName = context.packageName
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackageName")
            )
        )
    } catch (anfe: android.content.ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            )
        )
    }
}

@Preview
@Preview(name = "Splash Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashPreviewDark() {
    //SplashScreen({}, {}, {})
}