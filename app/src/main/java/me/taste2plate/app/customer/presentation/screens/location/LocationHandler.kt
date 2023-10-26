package me.taste2plate.app.customer.presentation.screens.location

import android.content.Context
import android.location.LocationManager

fun gpsEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

    return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

}
