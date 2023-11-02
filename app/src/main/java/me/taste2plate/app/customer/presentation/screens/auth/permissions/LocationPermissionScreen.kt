package me.taste2plate.app.customer.presentation.screens.auth.permissions

import android.Manifest
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.AllPermissionGranted
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun LocationPermissionScreen(
    onNavigateToLocationScreen: () -> Unit,) {


    var permissionGranted by remember {
        mutableStateOf(false)
    }

    var requestPermssion by remember {
        mutableStateOf(false)
    }


    if(requestPermssion) {
        requestPermssion = false
        AllPermissionGranted(permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ), onPermissionGranted = { granted ->
            permissionGranted = granted
        })
    }

    if(permissionGranted)
        onNavigateToLocationScreen()


    AppScaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(ScreenPadding)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(
                    text = "Permission Required",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                Text(
                    text = "Allow location permission to get your location",
                    fontWeight = FontWeight.Light
                )


            }

            MaterialIcon(
                imageVector =
                Icons.Default.LocationOn,
                tint = primaryColor.invoke(),
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )

            AppButton(
                text = "Allow Permission",
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
               requestPermssion = true
            }
        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LocationPermissionScreenPreview() {
    T2PCustomerAppTheme {
       // LocationPermissionScreen()
    }
}