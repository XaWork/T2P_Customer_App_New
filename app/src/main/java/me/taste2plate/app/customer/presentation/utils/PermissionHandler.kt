package me.taste2plate.app.customer.presentation.utils

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import me.taste2plate.app.customer.presentation.dialog.PermissionDialog
import me.taste2plate.app.customer.presentation.screens.location.gpsEnabled

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AllPermissionGranted(
    permissions: List<String>,
    onPermissionGranted: (Boolean) -> Unit
) {

    //Log.e("Permissions", "Button clicked")

    var granted = false
    val permissionStates = rememberMultiplePermissionsState(
        permissions = permissions
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    //conditional UI
    var showDialog by remember {
        mutableStateOf(false)
    }

    var permissionText by remember {
        mutableStateOf("This app needs access to your camera, location and microphone.\n Please allow all to move forward.")
    }

    var buttonText by remember {
        mutableStateOf("Grant Permission")
    }

    val context = LocalContext.current

   /* if (showDialog) {
        PermissionDialog(
            permissionText,
            onDismiss = {
                showDialog = it
            }, buttonText
        )
        //ShowToast(message = "Go to setting ", context = context)
    }*/

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    permissionStates.launchMultiplePermissionRequest()
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    if (permissionStates.allPermissionsGranted) {
        if (gpsEnabled(context)) {
            granted = true
        } else {
            permissionText = "Please enable GPS."
            buttonText = "Enable GPS"
            showDialog = true
            granted = false
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            permissionStates.permissions.forEach {
                when (it.permission) {
                    Manifest.permission.ACCESS_COARSE_LOCATION -> {
                        when {
                            it.status.isGranted -> {
                                //showDialog = false
                                granted = true
                            }

                            it.status.shouldShowRationale -> {
                                // showDialog = true
                                granted = false
                            }

                            !it.status.isGranted && !it.status.shouldShowRationale -> {
                                showDialog = true
                                granted = false
                            }
                        }
                    }

                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                        when {
                            it.status.isGranted -> {
                                //showDialog = false
                                granted = true
                            }

                            it.status.shouldShowRationale -> {
                                // showDialog = true
                                granted = false
                            }

                            !it.status.isGranted && !it.status.shouldShowRationale -> {
                                showDialog = true
                                granted = false
                            }
                        }
                    }

                    Manifest.permission.CAMERA -> {
                        when {
                            it.status.isGranted -> {
                                granted = true
                            }

                            it.status.shouldShowRationale -> {
                                granted = false
                            }

                            !it.status.isGranted && !it.status.shouldShowRationale -> {
                                granted = false
                                showDialog = false

                            }
                        }
                    }

                    Manifest.permission.RECORD_AUDIO -> {
                        when {
                            it.status.isGranted -> {
                                granted = true
                            }

                            it.status.shouldShowRationale -> {
                                granted = false
                            }

                            !it.status.isGranted && !it.status.shouldShowRationale -> {
                                granted = false
                                showDialog = true

                            }
                        }
                    }
                }
            }
        }
    }
    onPermissionGranted(granted)
}

/*
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions(
    permissions: List<String>,
    onNavigateToDeniedContentScreen: () -> Boolean,
    onNavigateToLocationScreen: () -> Unit
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions = permissions)

    //url for permission handle code
    //https://github.com/stevdza-san/PermissionsComposeDemo/blob/master/app/src/main/java/com/example/permissionstest/SinglePermission.kt

    HandleRequests(multiplePermissionsState = multiplePermissionsState,
        onNavigateToDeniedContentScreen = { shouldShowRationale ->
            if (shouldShowRationale)
                multiplePermissionsState.launchMultiplePermissionRequest()
            else
                onNavigateToLocationScreen()
        },
        onNavigateToLocationScreen = {
            onNavigateToLocationScreen()
        })

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandleRequests(
    multiplePermissionsState: MultiplePermissionsState,
    onNavigateToDeniedContentScreen: (Boolean) -> Unit,
    onNavigateToLocationScreen: () -> Unit
) {

    var shouldShowRationale by remember {
        mutableStateOf(false)
    }

    val result = multiplePermissionsState.permissions.all {
        shouldShowRationale = it.status.shouldShowRationale
        it.status == PermissionStatus.Granted
    }

    if (result)
        onNavigateToLocationScreen()
    else
        onNavigateToDeniedContentScreen(shouldShowRationale)
}*/
