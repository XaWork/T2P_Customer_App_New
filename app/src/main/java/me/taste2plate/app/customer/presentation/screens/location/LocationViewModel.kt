package me.taste2plate.app.customer.presentation.screens.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.hilt.android.lifecycle.HiltViewModel
import me.taste2plate.app.customer.data.UserPref
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val userPref: UserPref
): ViewModel() {

    var state by mutableStateOf(LocationState())


    fun onEvent(event: LocationEvent) {
        when (event) {
            is LocationEvent.GetCurrentLocation -> {
                currentLocation(event.context)
            }
        }
    }

    private fun currentLocation(
        context: Context
    ) {
        state = state.copy(isLoading = true)
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (gpsEnabled(context)) {
                fusedLocationClient.getCurrentLocation(
                    LocationRequest.QUALITY_HIGH_ACCURACY,
                    object : CancellationToken() {
                        override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                            CancellationTokenSource().token

                        override fun isCancellationRequested() = false
                    })
                    .addOnSuccessListener {
                        Timber.e("success to get location $it")

                        state = if (it != null) {
                            state.copy(isLoading = false, location = it)
                        }else{
                            state.copy(
                                isLoading = false,
                                error = "Failed to get location"
                            )
                        }
                    }
                    .addOnFailureListener {
                        Timber.e("Failed to get location $it")
                        state = state.copy(isLoading = false, location = null)
                    }
            } else {
                state = state.copy(isLoading = false, location = null)
            }
        } else {
            state = state.copy(isLoading = false, location = null)
            ActivityCompat.requestPermissions(
                context as Activity,  // Make sure context is an Activity
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0  // You need to define a unique request code
            )
        }
    }
}