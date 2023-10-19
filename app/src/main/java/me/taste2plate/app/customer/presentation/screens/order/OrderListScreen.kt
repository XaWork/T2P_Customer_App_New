package me.taste2plate.app.customer.presentation.screens.order

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.doneString
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.DrawableIcon

@Composable
fun OrderListScreen() {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "My Orders"
            ) {}
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MyOrderList()

            AppButton(
                text = doneString,
                modifier = Modifier.align(
                    Alignment.BottomCenter
                )
                    .padding(ScreenPadding)
            ) {}
        }
    }
}

@Composable
fun MyOrderList() {
    LazyColumn {
        items(10) {
            SingleOrderInfoItem()
        }
    }
}

@Composable
fun SingleOrderInfoItem() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .weight(3f)
                    .padding(ScreenPadding),
            ) {
                Text(
                    text = "24-04-2024",
                    fontSize = 18.sp
                )

                Text(
                    text = "T2P-134343124",
                    fontSize = 18.sp
                )

                Text(
                    text = "OTP",
                    fontSize = 20.sp
                )

                Text(
                    text = "Status",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            DrawableIcon(id = R.drawable.arrow_right, modifier = Modifier.weight(1f))
        }

        Divider()
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderListScreenPreview() {
    T2PCustomerAppTheme {
        OrderListScreen()
    }
}