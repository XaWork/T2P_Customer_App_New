package me.taste2plate.app.customer

import android.app.Application
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.libraries.places.api.Places
import com.google.firebase.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import me.taste2plate.app.customer.presentation.utils.mapApiKey
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject


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
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        Places.initialize(this.applicationContext, mapApiKey)
    }
}