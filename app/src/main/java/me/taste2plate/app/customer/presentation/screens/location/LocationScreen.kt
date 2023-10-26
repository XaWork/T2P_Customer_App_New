package me.taste2plate.app.customer.presentation.screens.location

import android.Manifest
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import me.taste2plate.app.customer.presentation.screens.home.widgets.SearchBar
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.AllPermissionGranted
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard

@Composable
fun LocationScreen(
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToNotificationScreen: () -> Unit,
    onNavigateToAddEditAddressScreen: () -> Unit,
) {

    var permissionGranted by remember {
        mutableStateOf(false)
    }

    AllPermissionGranted(permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    ), onPermissionGranted = { granted ->
        permissionGranted = granted
    })

    if (permissionGranted)
        LocationScreenContent(onNavigateToHomeScreen = {
            onNavigateToHomeScreen()
        }, onNavigateToAddEditAddressScreen = { onNavigateToAddEditAddressScreen() },
            onNavigateToNotificationScreen = onNavigateToNotificationScreen
        )

}

@Composable
fun LocationScreenContent(
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToNotificationScreen: () -> Unit,
    onNavigateToAddEditAddressScreen: () -> Unit,
) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(44.810058, 20.4617586), 17f)
    }

    var searchLocation by remember {
        mutableStateOf("")
    }
    AppScaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
                modifier = Modifier.fillMaxSize(),
                properties = MapProperties(isMyLocationEnabled = true),
                uiSettings = MapUiSettings(compassEnabled = true)
            ) {}

            SearchBar(value = searchLocation, onValueChange = {
                searchLocation = it
            }) {}

            RoundedCornerCard(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(ScreenPadding)
                ) {
                    //if (searchLocation.isNotEmpty())
                    AppButton(
                        text = "Confirm Location"
                    ) {
                        onNavigateToNotificationScreen()
                    }

                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onNavigateToAddEditAddressScreen() }) {
                        Text(
                            text = "Enter Location Manually",
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LocationScreenPreview() {
    T2PCustomerAppTheme {
        LocationScreen({}, {}, {})
    }
}