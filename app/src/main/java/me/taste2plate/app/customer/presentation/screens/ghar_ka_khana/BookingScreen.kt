package me.taste2plate.app.customer.presentation.screens.ghar_ka_khana

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar

@Composable
fun BookingScreen() {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = ""
            ) {}
        }
    ) {

    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    T2PCustomerAppTheme {
        BookingScreen()
    }
}