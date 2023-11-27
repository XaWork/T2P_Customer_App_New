package me.taste2plate.app.customer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.razorpay.Checkout
import dagger.hilt.android.AndroidEntryPoint
import me.taste2plate.app.customer.presentation.navigation.Navigation
import me.taste2plate.app.customer.presentation.screens.splash.SplashScreen
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
}