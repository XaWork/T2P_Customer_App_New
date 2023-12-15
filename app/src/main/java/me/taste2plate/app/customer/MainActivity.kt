package me.taste2plate.app.customer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
        co.setKeyID("rzp_test_2wlA7A5Vpf1BDo")

        setContent {
            T2PCustomerAppTheme {
                Navigation()
            }
        }
    }

    lateinit var onPayment: RazorpayPaymentSuccess
    override fun onPaymentSuccess(p0: String?) {
        Log.e("Payment", "Payment success $p0")
        onPayment.onPaymentSuccess(p0)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.e("Payment", "Payment error $p0\n$p1")
    }


    interface RazorpayPaymentSuccess {
        fun onPaymentSuccess(p0: String?)
    }

    fun setRazorpayPaymentSuccess(onSuccess: RazorpayPaymentSuccess) {
        this.onPayment = onSuccess
    }
}