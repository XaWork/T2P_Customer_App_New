package me.taste2plate.app.customer.presentation.screens.splash

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.DrawableIcon
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun UpdateAppScreen(
    onClick: () -> Unit,
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
                    text = "Update App",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                Text(
                    text = "Please update your app to get new features.",
                    fontWeight = FontWeight.Light
                )


            }

            DrawableImage(
                id = R.drawable.logo_new,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )

            AppButton(
                text = "Update",
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                onClick()
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