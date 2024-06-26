package me.taste2plate.app.customer.presentation.screens.order

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaOrderList
import me.taste2plate.app.customer.domain.model.user.OrderListModel
import me.taste2plate.app.customer.presentation.dialog.CustomDialog
import me.taste2plate.app.customer.presentation.dialog.SettingDialogType
import me.taste2plate.app.customer.presentation.dialog.SettingInfoDialog
import me.taste2plate.app.customer.presentation.theme.ExtraLowElevation
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.MediumSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RatingInfoRow
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.utils.toDate
import me.taste2plate.app.customer.utils.toDateObject
import me.taste2plate.app.customer.utils.toDecimal

@Composable
fun OrderDetailsScreen(
    viewModel: OrderViewModel,
    onNavigateToTrackOrderScreen: () -> Unit = {},
    onNavigateToHomeScreen: () -> Unit,
    navigateBack: () -> Unit,
) {
    val state = viewModel.state


    LaunchedEffect(true) {
        viewModel.onEvent(OrderEvent.GetOrderUpdate)
        viewModel.onEvent(
            OrderEvent.AddLog(
                LogRequest(
                    type = LogType.pageVisit,
                    event = "enter in order details screen",
                    page_name = "/order",
                    order_id = viewModel.selectedOrder?.id ?: ""
                )
            )
        )
    }

    var showOrderTrackDialog by remember {
        mutableStateOf(false)
    }
    if (showOrderTrackDialog)
        SettingInfoDialog(
            setting = state.setting!!,
            type = SettingDialogType.Track,
            onDismissRequest = { showOrderTrackDialog = false },
            onConfirmation = { showOrderTrackDialog = false }
        )

    var showCancelOrderDialog by remember {
        mutableStateOf(false)
    }
    if (showCancelOrderDialog)
        CustomDialog(
            title = "Cancel Order",
            text = "Are you sure, you want to cancel the order",
            confirmButtonText = "Yes",
            dismissButtonText = "No",
            onDismiss = {
                showCancelOrderDialog = false
            }
        ) {
            showCancelOrderDialog = false
            viewModel.onEvent(OrderEvent.CancelOrder)
        }

    if (state.cancelOrder != null) {
        CustomDialog(
            title = "",
            text = if (state.isError) state.cancelOrder.message
            else "Your order has been successfully cancelled"
        ) {
            viewModel.onEvent(OrderEvent.UpdateState)
            if (!state.isError)
                onNavigateToHomeScreen()
        }
    }

    AppScaffold(topBar = {
        AppTopBar(
            title = viewModel.selectedOrder?.orderid ?: viewModel.selectedGharKaKhanOrder?.orderid
            ?: ""
        ) { navigateBack() }
    }) {
        if (viewModel.selectedOrder == null && state.selectedTab == 0 ||
            viewModel.selectedGharKaKhanOrder == null && state.selectedTab == 1)
            AppEmptyView()
        else if (state.selectedTab == 0)
            ContentOrderDetailsScreen(
                state, viewModel.selectedOrder!!,
                showOrderTrackDialog = {
                    showOrderTrackDialog = true
                },
                showCancelOrderDialog = {
                    showCancelOrderDialog = true
                },
                onNavigateToTrackOrderScreen = {
                    onNavigateToTrackOrderScreen()
                }
            )
        else
            ContentGharKaKhanaOrderDetailsScreen(viewModel.selectedGharKaKhanOrder!!)

    }
}

@Composable
fun ContentOrderDetailsScreen(
    state: OrderState, order: OrderListModel.Result,
    showOrderTrackDialog: () -> Unit,
    showCancelOrderDialog: () -> Unit,
    onNavigateToTrackOrderScreen: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = ScreenPadding)
    ) {
        val orderItems = order.products
        items(orderItems) { item ->
            SingleOrderItem(item)
        }

        item {
            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))

            Text(
                text = "Delivery Date : ${order.deliveryDate.toDate("dd-MM-yyyy")} (${order.timeslot})",
                fontWeight = FontWeight.W500
            )

            VerticalSpace(space = SpaceBetweenViews)
        }

        //Price list
        val priceList = listOf(
            "Total Item Cost" to "$rupeeSign ${order.price.toDouble()}",
            "Shipping Charge" to "$rupeeSign ${order.totalShippingPrice.toDouble()}",
            "Packaging Charge" to "$rupeeSign ${order.totalPackingPrice.toDouble()}",
            if (order.totalIGST.toFloat() != 0.0f) "IGST" to "$rupeeSign ${order.totalIGST.toDouble()}"
            else {
                "CGST" to "$rupeeSign ${order.totalCGST.toDouble()}"
                "SGST" to "$rupeeSign ${order.totalSGST.toDouble()}"
            },
            if (order.coupon.isNotEmpty() && order.couponamount.isNotEmpty() && order.couponamount.toFloat() != 0f) "Applied Coupon" to "Rs -${order.couponamount.toFloat()}"
            else "" to "",
            if (order.pickupWeight.isNotEmpty() && order.pickupWeight.toFloat() > 0f) "Weight at Pickup" to "${order.pickupWeight} Kg"
            else "" to "",
            if (order.deliveryWeight.isNotEmpty() && order.deliveryWeight.toFloat() > 0f) "Weight at Delivery" to "${order.deliveryWeight} Kg"
            else "" to ""
        )

        items(priceList) {
            if (it.first.isNotEmpty())
                SpaceBetweenRow(modifier = Modifier.padding(vertical = LowPadding), item1 = {
                    Text(it.first, color = MaterialTheme.colorScheme.inverseSurface)
                }, item2 = {
                    Text(text = it.second, color = MaterialTheme.colorScheme.inverseSurface)
                })

        }

        item {
            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))

            SpaceBetweenRow(modifier = Modifier.padding(vertical = SpaceBetweenViewsAndSubViews),
                item1 = {
                    Text(
                        "Final Total", fontSize = 20.sp, fontWeight = FontWeight.W500
                    )
                },
                item2 = {
                    Text(
                        "$rupeeSign ${order.finalprice.toDouble()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                })

            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))

            if (state.orderUpdates.isNotEmpty()) {
                Text("Update", fontSize = 20.sp)
                VerticalSpace(space = SpaceBetweenViews)
            }
        }

        if (state.orderUpdates.isNotEmpty()) itemsIndexed(state.orderUpdates) { _, item ->
            SingleUpdateItem("${item.note}\n${item.createdDate.toDate()}")
        }


        item {
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500
                    )
                ) {
                    append("Track Order")
                }
            }

            if (!order.status.contentEquals(OrderStatus.cancel.name))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable {
                            if (order.status.contentEquals(OrderStatus.delivery_boy_started.name)) {
                                onNavigateToTrackOrderScreen()
                            } else
                                showOrderTrackDialog()
                        },
                    text = annotatedString,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End
                )

            VerticalSpace(space = MediumSpacing)

            Text("Shipping To", fontSize = 20.sp)

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            RoundedCornerCard(
                modifier = Modifier.fillMaxWidth(), cardColor = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary
                ), elevation = 4.dp
            ) {
                val address = order.address
                Text(
                    "${address.address}\n${address.address2}\n${address.city.name}\n${address.state.name}\nPin : ${address.pincode}",
                    modifier = Modifier.padding(ScreenPadding)
                )
            }

            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))

            val serverTimeDate = state.serverTime!!.toDateObject("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val createdAt = order.createdDate.toDateObject("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val diffHours: Long = (serverTimeDate.time - createdAt.time) / (60 * 60 * 1000)
            if (diffHours < 4 && !order.status.contentEquals("cancel"))
                if (state.isLoading) ShowLoading()
                else AppButton(
                    text = "Cancel Order"
                ) { showCancelOrderDialog() }
        }
    }
}

@Composable
fun ContentGharKaKhanaOrderDetailsScreen(
    order: GharKaKhanaOrderList.Result,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = ScreenPadding)
    ) {
        item{
            VerticalSpace(space = SpaceBetweenViewsAndSubViews)
        }

        val orderItems = order.products!!
        items(orderItems) { item ->
            SingleGharKaKhanOrderItem(
                item!!
            )
        }

        item {
            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))

            Text(
                text = "Pickup Date : ${order.pickupDate!!.toDate("dd-MM-yyyy")} \n(${order.pickupTime})",
                fontWeight = FontWeight.W500
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                text = "Delivery Date : ${order.deliveryDate!!.toDate("dd-MM-yyyy")} (${order.deliveryTimeslot})",
                fontWeight = FontWeight.W500
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                text = "Delivery Type : ${order.deliveryType?.uppercase()}",
                fontWeight = FontWeight.W500
            )

            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))
        }

        //Price list
        val priceList = listOf(
            "Total Price" to "$rupeeSign ${order.totalPrice?.toDecimal()}",
            "Pickup Price" to "$rupeeSign ${order.pickupPrice?.toDecimal()}",
            "Delivery Price" to "$rupeeSign ${order.deliveryPrice?.toDecimal()}",
            "SGST" to "$rupeeSign ${if(!order.sgst.isNullOrEmpty()) order.sgst.toDecimal() else ""}",
            "CGST" to "$rupeeSign ${if(!order.cgst.isNullOrEmpty()) order.cgst.toDecimal() else ""}",
            "IGST" to "$rupeeSign ${if(!order.igst.isNullOrEmpty()) order.igst.toDecimal() else ""}",
        )

        items(priceList) {
            if (it.first.isNotEmpty())
                SpaceBetweenRow(modifier = Modifier.padding(vertical = LowPadding), item1 = {
                    Text(it.first, color = MaterialTheme.colorScheme.inverseSurface)
                }, item2 = {
                    Text(text = it.second, color = MaterialTheme.colorScheme.inverseSurface)
                })

        }

        item{
            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))
        }

        //Distance list
        val distanceList = listOf(
            "Pickup Distance" to "${order.pickupDistance?.toDecimal()} KM",
            "Delivery Distance" to "${order.deliveryDistance?.toDecimal()} KM",
        )

        items(distanceList) {
            if (it.first.isNotEmpty())
                SpaceBetweenRow(modifier = Modifier.padding(vertical = LowPadding), item1 = {
                    Text(
                        it.first,
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.W600
                    )
                }, item2 = {
                    Text(
                        text = it.second,
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.W600
                    )
                })

        }

        item {
            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))

            SpaceBetweenRow(modifier = Modifier.padding(vertical = SpaceBetweenViewsAndSubViews),
                item1 = {
                    Text(
                        "Total Weight", fontSize = 20.sp, fontWeight = FontWeight.W500
                    )
                },
                item2 = {
                    Text(
                        "${order.totalWeight?.toDecimal()} KG",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                })

            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))
        }
    }
}

@Composable
fun SingleGharKaKhanOrderItem(
    item: GharKaKhanaOrderList.Result.Product,
) {
    RoundedCornerCard(
        modifier = Modifier
            .padding(bottom = VeryLowSpacing),
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = ExtraLowElevation
    ) {
        Column(
            modifier = Modifier
                .padding(ScreenPadding)
                .fillMaxWidth(),
        ) {
            Text(
                item.name!!,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text("Weight : ${item.weight} kg")

        }
    }
}

@Composable
fun SingleOrderItem(
    item: OrderListModel.Result.Product
) {
    RoundedCornerCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = VeryLowSpacing),
        cardColor = CardDefaults.cardColors(
            containerColor = screenBackgroundColor.invoke()
        )
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(ScreenPadding)
            ) {
                NetworkImage(
                    image = item.product.file[0].location,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(MediumRoundedCorners)),
                    contentScale = ContentScale.Crop
                )

                HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

                Text(
                    text = item.product.name,
                    fontSize = 18.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider()

            RatingInfoRow(
                flatOff = "${rupeeSign}${item.product.price} x ${item.quantity}",
                rating = "$rupeeSign ${item.product.price.toInt() * item.quantity.toInt()}",
                showIcon = false,
                modifier = Modifier.padding(ScreenPadding),
                weight = item.product.weight
            )
        }
    }
}

@Composable
fun SingleUpdateItem(
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp),
            contentAlignment = Alignment.Center
        ) {
            StraightLine(modifier = Modifier.fillMaxWidth())
            Circle(modifier = Modifier.align(Alignment.Center))
        }
        Text(text = text, fontSize = 12.sp)
    }
}

@Composable
fun Circle(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier.size(10.dp)
    ) {
        val radius = size.width / 2
        drawCircle(
            color = Color.Red, radius = radius
        )
    }
}

@Composable
fun StraightLine(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
    ) {
        val startY = 0f
        val endY = size.height
        val lineLength = size.width / 2 // Adjust the length as needed

        drawLine(
            color = Color.Green,
            start = Offset(size.width / 2, startY),
            end = Offset(size.width / 2, endY),
            strokeWidth = 3.dp.toPx()
        )
    }
}

