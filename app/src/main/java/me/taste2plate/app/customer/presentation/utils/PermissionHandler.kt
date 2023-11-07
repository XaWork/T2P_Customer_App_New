package me.taste2plate.app.customer.presentation.utils

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions(
    permission: List<String>,
    permissionStatus: (granted: Boolean) -> Unit
) {
    val permissionState = rememberMultiplePermissionsState(permissions = permission)

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                permissionState.launchMultiplePermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    var isGranted = false
    permissionState.permissions.forEach { perm ->
        when (perm.permission) {
            Manifest.permission.ACCESS_COARSE_LOCATION -> {
                when {
                    perm.status.isGranted -> {
                        isGranted = true
                    }

                    perm.status.shouldShowRationale -> {
                        isGranted = false
                    }

                    perm.isPermanentlyDeclined() -> {
                        isGranted = false
                    }
                }
            }

            Manifest.permission.ACCESS_FINE_LOCATION -> {
                when {
                    perm.status.isGranted -> {
                        isGranted = true
                    }

                    perm.status.shouldShowRationale -> {
                        isGranted = false
                    }

                    perm.isPermanentlyDeclined() -> {
                        isGranted = false
                    }
                }
            }

            Manifest.permission.POST_NOTIFICATIONS -> {
                when {
                    perm.status.isGranted -> {
                        isGranted = true
                    }

                    perm.status.shouldShowRationale -> {
                        isGranted = false
                    }

                    perm.isPermanentlyDeclined() -> {
                        isGranted = false
                    }
                }
            }
        }
    }
    permissionStatus(isGranted)
}

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanentlyDeclined(): Boolean {
    return !status.shouldShowRationale && status.isGranted
}