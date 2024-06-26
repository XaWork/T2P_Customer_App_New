package me.taste2plate.app.customer.presentation.screens.order

import android.util.Log
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaOrderList
import me.taste2plate.app.customer.domain.model.user.OrderListModel
import me.taste2plate.app.customer.presentation.theme.ForestGreen
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.doneString
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
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
    onNavigateToOrderDetailsScreen: () -> Unit,
    navigateBack: () -> Unit,
) {
    val state = viewModel.state

    LaunchedEffect(true) {
        viewModel.onEvent(
            OrderEvent.AddLog(
                LogRequest(
                    type = LogType.pageVisit,
                    event = "enter in order list screen",
                    page_name = "/order"
                )
            )
        )

        viewModel.selectedOrder = null
        viewModel.selectedGharKaKhanOrder = null
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "My Orders"
            ) { navigateBack() }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                TabRow(selectedTabIndex = state.selectedTab) {
                    state.tabs.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    text = title,
                                    color = if (state.selectedTab == index)
                                        primaryColor.invoke()
                                    else
                                        MaterialTheme.colorScheme.inverseSurface
                                )
                            },
                            selected = index == state.selectedTab,
                            onClick = {
                                viewModel.onEvent(OrderEvent.ChangeTab(index))
                            })
                    }
                }

                if (state.isLoading)
                    ShowLoading(isButton = false)
                else if (state.selectedTab == 0 && state.orderList.isEmpty() ||
                    state.selectedTab == 1 && state.gharKaKhanaOrderList.isEmpty()
                )
                    AppEmptyView()
                else {
                    Log.e(
                        "Order",
                        "Select tab is ${state.selectedTab}\nOrer List size ${state.orderList.size}\nGhar ka khana order list size ${state.gharKaKhanaOrderList.size}"
                    )
                    if (state.selectedTab == 0)
                        MyOrderList(
                            state.orderList,
                            emptyList(),
                            onClick = {
                                viewModel.selectedOrder = it
                                onNavigateToOrderDetailsScreen()
                            }
                        )
                    else
                        MyOrderList(
                            emptyList(),
                            state.gharKaKhanaOrderList,
                            gharkaKhanaOnClick = {
                                viewModel.selectedGharKaKhanOrder = it
                                onNavigateToOrderDetailsScreen()
                            }
                        )
                }
            }

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
    gharKaKhanaOrderList: List<GharKaKhanaOrderList.Result?>,
    onClick: (order: OrderListModel.Result) -> Unit = {},
    gharkaKhanaOnClick: (order: GharKaKhanaOrderList.Result) -> Unit = {},
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = SpaceBetweenViews,
            bottom = 70.dp
        )
    ) {
        if (orderList.isNotEmpty())
            items(orderList) { order ->
                SingleOrderInfoItem(
                    order,
                    null,
                    onClick = {
                        onClick(order)
                    }
                )
            }
        else
            items(gharKaKhanaOrderList) { order ->
                SingleOrderInfoItem(
                    null,
                    order,
                    onClick = {
                        gharkaKhanaOnClick(order!!)
                    }
                )
            }
    }
}

@Composable
fun SingleOrderInfoItem(
    order: OrderListModel.Result?,
    gharKaKhanaOrder: GharKaKhanaOrderList.Result?,
    onClick: () -> Unit
) {
    BlackBorderCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary
        ),
        borderWidth = 0.3.dp
    ) {
        Column(modifier = Modifier.noRippleClickable {
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
                        info = order?.createdDate?.toDate()
                            ?: gharKaKhanaOrder!!.createdAt!!.toDate(),
                        fontSize = 18.sp
                    )
                    InfoWithIcon(
                        modifier = Modifier.padding(LowPadding),
                        icon = true,
                        imageVector = Icons.Outlined.Info,
                        info = order?.orderid ?: gharKaKhanaOrder!!.orderid,
                        fontSize = 18.sp
                    )

                    if (order != null)
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
                order?.status?.uppercase() ?: gharKaKhanaOrder!!.status!!.uppercase(),
                color = ForestGreen,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(ScreenPadding)
            )
        }
    }
}

