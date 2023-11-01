package me.taste2plate.app.customer.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.MainActivity
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.data.UserPref
import javax.inject.Inject

const val channelId = "channelId"
const val channelName = "channelName"

@AndroidEntryPoint
class FirebaseInstanceService : FirebaseMessagingService() {

    @Inject
    lateinit var userPref: UserPref

    override fun onNewToken(token: String) {
        Log.e("Token", "token in firebase instance service class: $token")

        CoroutineScope(Dispatchers.IO).launch {
            userPref.saveToken(token)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null) {
            Log.e("notification message", "onMessageReceived: ${message.notification!!.title!!}")
            addNotification(message.notification!!.title!!, message.notification!!.body!!)
        }
    }

    private fun addNotification(title: String, desc: String) {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.t2p_logo)
            .setContentTitle(title)
            .setContentText(desc)
            .setAutoCancel(true)

        val notificationIntent = Intent(
            this,
            MainActivity::class.java
        )
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        /*val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )*/

        builder.setContentIntent(pendingIntent)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())
    }
}