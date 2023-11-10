package me.taste2plate.app.customer.presentation.screens.permissions

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.forestGreen
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun NotificationPermissionScreen(
    onNavigateToHomeScreen: () -> Unit
) {

    var permissionGranted by remember {
        mutableStateOf(false)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        RequestPermissions(
            permission = listOf(
                Manifest.permission.POST_NOTIFICATIONS
            ),
            permissionStatus = { granted ->
                permissionGranted = granted
            }
        )
    else
        permissionGranted = true

    if (!permissionGranted)
        NotificationScreenContent()
    else
        AllSetupContent {
            onNavigateToHomeScreen()
        }

}

@Composable
fun NotificationScreenContent() {
    val context = LocalContext.current
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
                    text = "Get regular update of your order & offers.",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                Text(
                    text = "Allow push notification to get real-time update on your orders",
                    fontWeight = FontWeight.Light
                )


            }

            MaterialIcon(
                imageVector =
                Icons.Default.Notifications,
                tint = primaryColor.invoke(),
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )

            AppButton(
                text = "Turn on notifications",
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                )
                context.startActivity(intent)
            }
        }
    }
}

@Composable
fun AllSetupContent(
    onNavigateToHomeScreen: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToHomeScreen()
    }

    AppScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "All Setup",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                text = "Welcome to T2P Family",
                fontWeight = FontWeight.Light
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            MaterialIcon(
                imageVector =
                Icons.Default.CheckCircle,
                tint = forestGreen.invoke(),
                modifier = Modifier
                    .size(200.dp)
            )

        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NotificationPermissionScreenPreview() {
    T2PCustomerAppTheme {
        //  NotificationPermissionScreen({})
    }
}