package me.taste2plate.app.customer.presentation.screens.order

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.domain.model.user.OrderListModel
import me.taste2plate.app.customer.presentation.theme.ForestGreen
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.doneString
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.BlackBorderCard
import me.taste2plate.app.customer.presentation.widgets.DrawableIcon
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.utils.toDate

@Composable
fun OrderListScreen(
    viewModel: OrderViewModel,
    onNavigateToOrderDetailsScreen: () -> Unit
) {
    val state = viewModel.state

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "My Orders"
            ) {}
        }
    ) {
        if (state.isLoading)
            ShowLoading()
        else if (state.orderList.isEmpty())
            AppEmptyView()
        else
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                MyOrderList(
                    state.orderList,
                    onClick = {
                        viewModel.selectedOrder = it
                        onNavigateToOrderDetailsScreen()
                    }
                )

                AppButton(
                    text = doneString,
                    modifier = Modifier
                        .align(
                            Alignment.BottomCenter
                        )
                        .padding(ScreenPadding)
                ) {}
            }
    }
}

@Composable
fun MyOrderList(
    orderList: List<OrderListModel.Result>,
    onClick: (order: OrderListModel.Result) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = SpaceBetweenViews,
            bottom = 70.dp
        )
    ) {
        items(orderList) { order ->
            SingleOrderInfoItem(
                order,
                onClick = {
                    onClick(order)
                }
            )
        }
    }
}

@Composable
fun SingleOrderInfoItem(
    order: OrderListModel.Result,
    onClick: () -> Unit
) {
    BlackBorderCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary
        ),
        borderWidth = 0.3.dp
    ) {
        Column(modifier = Modifier.clickable {
            onClick()
        }) {
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
                    InfoWithIcon(
                        modifier = Modifier.padding(LowPadding),
                        icon = true,
                        imageVector = Icons.Outlined.DateRange,
                        info = order.createdDate.toDate(),
                        fontSize = 18.sp
                    )
                    InfoWithIcon(
                        modifier = Modifier.padding(LowPadding),
                        icon = true,
                        imageVector = Icons.Outlined.Info,
                        info = order.orderid,
                        fontSize = 18.sp
                    )
                    InfoWithIcon(
                        modifier = Modifier.padding(LowPadding),
                        icon = true,
                        imageVector = Icons.Outlined.PlayArrow,
                        info = "OTP : ${order.otp}",
                        fontSize = 18.sp
                    )
                }

                DrawableIcon(id = R.drawable.arrow_right, modifier = Modifier.weight(1f))
            }

            Divider()

            Text(
                order.status.uppercase(),
                color = ForestGreen,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(ScreenPadding)
            )
        }
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderListScreenPreview() {
    T2PCustomerAppTheme {
        //OrderListScreen({})
    }
}