package me.taste2plate.app.customer.presentation.screens.auth.permissions

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun NotificationPermissionScreen(
    onNavigateToHomeScreen : () -> Unit
) {
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
                onNavigateToHomeScreen()
            }
        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NotificationPermissionScreenPreview() {
    T2PCustomerAppTheme {
        NotificationPermissionScreen({})
    }
}