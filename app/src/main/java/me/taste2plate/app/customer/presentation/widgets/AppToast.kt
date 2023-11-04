package me.taste2plate.app.customer.presentation.widgets

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.taste2plate.app.customer.T2PApp

fun showToast(
    message: String,
    toastLength: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(T2PApp.applicationContext(), message, toastLength).show()
}