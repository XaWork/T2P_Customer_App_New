package me.taste2plate.app.customer.presentation.screens.location

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import me.taste2plate.app.customer.presentation.screens.permissions.LocationPermissionScreen
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.RequestPermissions
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.showToast
import timber.log.Timber

/**
-> Check location permission
if not allowed then send user to location permission screen
if allowed then
- first check gps
if it enables then get current location and update camera position to current location
if not enabled then set default camera position
- after click on any location from search list and user tap on map
update mark and user location in variable
- after click submit button add user address

source : https://blog.sanskar10100.dev/integrating-google-maps-places-api-and-reverse-geocoding-with-jetpack-compose
 **/

@Composable
fun LocationScreen(
    onNavigateToNotificationScreen: () -> Unit,
    onNavigateToAddEditAddressScreen: () -> Unit,
    onNavigateBackToAddEditAddressScreen: (location: Location) -> Unit,
    viewModel: LocationViewModel = hiltViewModel()
) {

    var permissionGranted by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    RequestPermissions(permission = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    ), permissionStatus = {
        permissionGranted = it
    })

    if (permissionGranted)
        LocationScreenContent(
            onNavigateToAddEditAddressScreen = { onNavigateToAddEditAddressScreen() },
            onNavigateToNotificationScreen = {
                onNavigateToNotificationScreen()
            },
            viewModel = viewModel
        )
    else
        LocationPermissionScreen {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.packageName, null)
            )
            context.startActivity(intent)
        }
}

@Composable
fun LocationScreenContent(
    onNavigateToAddEditAddressScreen: () -> Unit,
    onNavigateToNotificationScreen: () -> Unit,
    viewModel: LocationViewModel
) {
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.currentLatLong, 13f)
    }
    val mapUiSettings by remember { mutableStateOf(MapUiSettings()) }
    val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }

    LaunchedEffect(Unit) {
        viewModel.placesClient = Places.createClient(context)
        viewModel.geoCoder = Geocoder(context)
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            viewModel.getAddress(cameraPositionState.position.target)
        }
    }

    LaunchedEffect(viewModel.currentLatLong) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLng(viewModel.currentLatLong))
    }

    AppScaffold(
        topBar = {
            MapAppBar(viewModel)
        },
        bottomBar = {
            MapBottomSheet(
                onNavigateToAddEditAddressScreen = {
                    onNavigateToAddEditAddressScreen()
                },
                onNavigateToNotificationScreen = {
                    onNavigateToNotificationScreen()
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
                modifier = Modifier.fillMaxSize(),
                properties = mapProperties,
                uiSettings = mapUiSettings,/*
                onMapClick = {
                    scope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.newLatLng(it))
                    }
                },*/
                onMyLocationButtonClick = {
                    if (gpsEnabled(context))
                        false
                    else {
                        showToast("Please enable GPS")
                        false
                    }
                },
                onMyLocationClick = { location ->
                    Timber.e("Current Location is $location")
                    val latLng = LatLng(location.latitude, location.longitude)
                    viewModel.currentLatLong = latLng
                }
            )

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = primaryColor.invoke(),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )

        }
    }
}

@Composable
fun MapAppBar(
    viewModel: LocationViewModel
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = viewModel.text, onValueChange = {
                    viewModel.text = it
                    viewModel.searchPlaces(it)
                }, modifier = Modifier
                    .fillMaxWidth()
            )

            AnimatedVisibility(
                viewModel.locationAutofill.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.locationAutofill) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(RectangleShape)
                                .clickable {
                                    viewModel.text = it.address
                                    viewModel.locationAutofill.clear()
                                    viewModel.getCoordinates(it)
                                }
                            /*.clickWithRipple {
                            }*/
                        ) {
                            Text(it.address)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun MapBottomSheet(
    modifier: Modifier = Modifier,
    onNavigateToAddEditAddressScreen: () -> Unit,
    onNavigateToNotificationScreen: () -> Unit,
) {
    RoundedCornerCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(ScreenPadding)
        ) {
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


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LocationScreenPreview() {
    T2PCustomerAppTheme {
        // LocationScreen({}, {}, {})
    }
}

data class AutocompleteResult(
    val address: String,
    val placeId: String
)