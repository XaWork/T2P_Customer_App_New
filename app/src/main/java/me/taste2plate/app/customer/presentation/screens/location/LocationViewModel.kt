package me.taste2plate.app.customer.presentation.screens.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationRequest
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.taste2plate.app.customer.data.UserPref
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val userPref: UserPref
) : ViewModel() {

    var state by mutableStateOf(LocationState())
    private var job: Job? = null
    val locationAutofill = mutableStateListOf<AutocompleteResult>()
    lateinit var placesClient: PlacesClient
    lateinit var geoCoder: Geocoder
    var currentLatLong by mutableStateOf(LatLng(28.64, 77.27))

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
                        Log.e("location", "success to get location $it")

                        state = if (it != null) {
                            state.copy(isLoading = false, location = it)
                        } else {
                            state.copy(
                                isLoading = false,
                                error = "Failed to get location"
                            )
                        }
                    }
                    .addOnFailureListener {
                        Log.e("location", "Failed to get location $it")
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

    fun searchPlaces(query: String) {
        job?.cancel()
        locationAutofill.clear()
        job = viewModelScope.launch {
            val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()
            placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
                locationAutofill += response.autocompletePredictions.map {
                    AutocompleteResult(
                        it.getFullText(null).toString(), it.placeId
                    )
                }
            }.addOnFailureListener {
                it.printStackTrace()
                println(it.cause)
                println(it.message)
            }
        }
    }

    fun getCoordinates(result: AutocompleteResult) {
        val placeFields = listOf(Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(result.placeId, placeFields)
        placesClient.fetchPlace(request).addOnSuccessListener {
            if (it != null) {
                currentLatLong = it.place.latLng!!
            }
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }

    var text by mutableStateOf("")

    fun getAddress(latLng: LatLng) {
        try {
            currentLatLong = latLng
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                           geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1){
                               text = it[0].getAddressLine(0)
                           }
                       }else{
                           val address = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                           if (address?.isNotEmpty() == true) {
                               text = address[0]?.getAddressLine(0).toString()
                           }
                       }
                    } catch (e: IOException) {
                        // Handle IOException appropriately
                        Log.e("Error", e.toString())
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.printStackTrace().toString())
        }
    }
}