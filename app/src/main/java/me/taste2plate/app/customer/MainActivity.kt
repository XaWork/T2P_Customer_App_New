package me.taste2plate.app.customer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import me.taste2plate.app.customer.presentation.navigation.Navigation
import me.taste2plate.app.customer.presentation.screens.splash.SplashScreen
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID("rzp_live_ZLgzjgdHBJDlP8")

        setContent {
            T2PCustomerAppTheme {
                Navigation()
            }
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    private var doubleBackToExitPressedOnce = false
}