package me.taste2plate.app.customer.presentation.screens.location

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Geocoder
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import me.taste2plate.app.customer.presentation.navigation.Screens
import me.taste2plate.app.customer.presentation.screens.permissions.GPSEnableScreen
import me.taste2plate.app.customer.presentation.screens.permissions.LocationPermissionScreen
import me.taste2plate.app.customer.presentation.screens.permissions.RequestPermissions
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.showToast
import java.util.Locale

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
    screen: String? = null,
    onNavigateToNotificationScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    onNavigateBackToAddEditAddressScreen: (latLng: LatLng) -> Unit,
    viewModel: LocationViewModel = hiltViewModel()
) {

    Log.e("LocationScreen", "Screen is $screen")

    var permissionGranted by remember {
        mutableStateOf(false)
    }
    var gpsEnabled by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    RequestPermissions(permission = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    ), permissionStatus = {
        permissionGranted = it
    })

    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = viewModel.state) {
        if (viewModel.state.addressSavedLocally)
            onNavigateToHomeScreen()
    }

    LaunchedEffect(
        permissionGranted && gpsEnabled && viewModel.currentLatLong == LatLng(
            28.64,
            77.27
        )
    ) {
        isLoading = true
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000L,
        ).build()
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context as Activity)
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                for (lo in p0.locations) {
                    if (viewModel.currentLatLong == LatLng(28.64, 77.27))
                        viewModel.currentLatLong = LatLng(lo.latitude, lo.longitude)
                }
                isLoading = false
            }
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                gpsEnabled = gpsEnabled(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    if (permissionGranted)
        if (gpsEnabled)
            if (isLoading)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ShowLoading()
                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)
                    Text("Fetching your location. Please Wait...", color = Color.Black)
                }
            else
                LocationScreenContent(
                    onNavigateToNotificationScreen = {
                        when (screen) {
                            null -> onNavigateToNotificationScreen()
                            Screens.HomeScreen.route -> viewModel.onEvent(LocationEvent.GetCityFromZip)
                            else -> onNavigateBackToAddEditAddressScreen(viewModel.currentLatLong)
                        }
                    },
                    viewModel = viewModel
                )
        else
            GPSEnableScreen {
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
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
        viewModel.geoCoder = Geocoder(context, Locale.getDefault())
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
                viewModel = viewModel,
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
                    Log.e("location", "Current Location is $location")
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

           // Text("Delete & Change the Pickup/ Delivery Address if it is Different")

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
                                .noRippleClickable {
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
    viewModel: LocationViewModel,
    onNavigateToNotificationScreen: () -> Unit,
) {
    RoundedCornerCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(ScreenPadding)
        ) {
            if (viewModel.state.isLoading)
                ShowLoading()
            else
                AppButton(
                    text = "Confirm Location"
                ) {
                    onNavigateToNotificationScreen()
                }

            /* if (screen == null)
                 TextButton(
                     modifier = Modifier.fillMaxWidth(),
                     onClick = {  }) {
                     Text(
                         text = "Enter Location Manually",
                         textAlign = TextAlign.Center,
                     )
                 }*/
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