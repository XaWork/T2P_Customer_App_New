package me.taste2plate.app.customer

import android.app.Application
import android.content.Context
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp
import me.taste2plate.app.customer.presentation.utils.mapApiKey


@HiltAndroidApp
class T2PApp: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: T2PApp? = null

        var wishlistCount = 0
        var cartCount = 0
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)
        Places.initialize(this.applicationContext, mapApiKey)
    }
}