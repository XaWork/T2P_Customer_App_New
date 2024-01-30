package me.taste2plate.app.customer.presentation.screens.ghar_ka_khana

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.presentation.screens.address.SingleAddressItem
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryType
import me.taste2plate.app.customer.presentation.screens.checkout.PriceData
import me.taste2plate.app.customer.presentation.screens.checkout.fontSize
import me.taste2plate.app.customer.presentation.screens.checkout.fontSize1
import me.taste2plate.app.customer.presentation.theme.ExtraLowPadding
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowSpacing
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppRadioButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.HeadingText
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.RadioButtonInfo
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.TextInCircle
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.utils.toDate
import me.taste2plate.app.customer.utils.toDecimal

@Composable
fun ConfirmBookingScreen(
    viewModel: GharKaKhanaViewModel,
    moveToHomeScreen: () -> Unit,
    navigateBack: () -> Unit,
) {

    LaunchedEffect(key1 = viewModel.state) {
        if (viewModel.state.confirmCheckout != null && viewModel.state.confirmCheckout!!.status == Status.success.name) {
            moveToHomeScreen()
        }
    }
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Any Food 2 Any Place"
            ) { navigateBack() }
        }
    ) {
        if (viewModel.state.isLoading)
            ShowLoading(isButton = false)
        else
            ContentConfirmBooking(viewModel)
    }
}

@Composable
fun ContentConfirmBooking(
    viewModel: GharKaKhanaViewModel,
) {
    val state = viewModel.state

    LazyColumn(
        modifier = Modifier
            .padding(ScreenPadding)
    ) {
        item {
            HeadingText(
                "Pickup Location",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = ExtraLowPadding)
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            SingleAddressItem(address = state.pickupLocation!!, defaultAddress = null)

            HeadingText(
                "Destination Location",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    top = SpaceBetweenViewsAndSubViews,
                    bottom = ExtraLowPadding
                )
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            SingleAddressItem(address = state.destinationLocation!!, defaultAddress = null)

            AppDivider(
                thickness = 1.dp, modifier = Modifier.padding(
                    vertical = ExtraLowPadding
                )
            )

            HeadingText(
                "Products You Added",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    bottom = LowPadding
                )
            )

        }

        items(state.cartItems) {
            SingleFoodItem(it,
                buttonVisible = false,
                onDelete = {
                    viewModel.onEvent(GharKaKhanaEvent.DeleteCart(it.id))
                })
        }

        item {
            AppDivider(
                thickness = 1.dp, modifier = Modifier.padding(
                    horizontal = ExtraLowPadding
                )
            )
        }

        val data = state.checkout!!
        val totalDistance =
            if (data.pickupDistance.isNullOrEmpty() && data.deliveryDistance.isNullOrEmpty()) 0.0
            else data.pickupDistance!!.toDecimal() + data.deliveryDistance!!.toDecimal()

        val longDistanceExtraPickupCost =
            if (data.pickUpFreeDistance.isNullOrEmpty() && data.multiplierForPickup.isNullOrEmpty())
                0.0
            else
                (totalDistance - data.pickUpFreeDistance!!.toDecimal()) * data.multiplierForPickup!!.toDecimal()

        val longDistanceExtraDeliveryCost =
            if (data.deliveryFreeDistance.isNullOrEmpty() && data.multiplierForDelivery.isNullOrEmpty())
                0.0
            else
                (totalDistance - data.deliveryFreeDistance!!.toDecimal()) * data.multiplierForDelivery!!.toDecimal()

        Log.e("Distance", "Delivery Distance : ${data.deliveryDistance}\nPickup Distance : ${data.pickupDistance}" +
                "Total Distance : $totalDistance" +
                "Calulate Delivery Cost : ($totalDistance - ${data.deliveryFreeDistance}) * ${data.multiplierForDelivery}")

        Log.e("Distance", "Delivery Distance : ${data.deliveryDistance}\nPickup Distance : ${data.pickupDistance}" +
                "Total Distance : $totalDistance" +
                "Calulate Pikckup Cost : ($totalDistance - ${data.pickUpFreeDistance}) * ${data.multiplierForPickup}")

        val priceList = listOf(
            PriceData(
                title = "Billing Summery\n",
                price = "",
                bold = true
            ),
            PriceData(
                title = "Shipping Cost\n",
                price = rupeeSign + data.shippingPrice?.toDecimal().toString() + "\n"
            ),
            PriceData(
                title = "Long Distance Extra Pickup Cost",
                price = rupeeSign + "$longDistanceExtraPickupCost",
                subTitle = "(Total Distance - Free Distance) Km x Rate / Km\n($totalDistance - ${data.pickUpFreeDistance}) Km x $rupeeSign${data.multiplierForPickup} / Km\n"
            ),
            PriceData(
                title = "Long Distance Extra Delivery Cost",
                price = rupeeSign + "$longDistanceExtraDeliveryCost",
                subTitle = "(Total Distance - Free Distance) Km x Rate / Km\n($totalDistance - ${data.deliveryFreeDistance}) Km x $rupeeSign${data.multiplierForDelivery} / Km\n"
            ),
            PriceData(
                title = "CGST",
                price = rupeeSign + data.cgst?.toDecimal().toString()
            ),
            PriceData(
                title = "SGST",
                price = rupeeSign + data.sgst?.toDecimal().toString()
            ),
            PriceData(
                title = "IGST\n",
                price = rupeeSign + data.igst?.toDecimal().toString()
            ),
            PriceData(
                title = "Total Estimated Weight\n",
                bold = true,
                price = "${data.totalWeight?.toDecimal().toString()} Kg\n"
            ),
            PriceData(
                title = "Total estimated Shipping Cost\n",
                bold = true,
                price = "$rupeeSign${data.totalPrice?.toDecimal().toString()}\n"
            ),

/*
            PriceData(
                title = "Pickup Price",
                price = rupeeSign + data.pickupPrice?.toDecimal().toString()
            ),
            PriceData(
                title = "Delivery Price\n",
                price = "$rupeeSign ${data.deliveryPrice?.toDecimal().toString()}\n"
            ),
            PriceData(
                title = "Total Estimate Cost",
                price = rupeeSign + data.totalPrice?.toDecimal().toString(),
                bold = true
            ),
            PriceData(
                title = "Pickup Distance",
                price = "${data.pickupDistance?.toDecimal().toString()} Km"
            ),
            PriceData(
                title = "Delivery Distance",
                price = "${data.deliveryDistance?.toDecimal().toString()} Km"
            ),*/
        )

        items(
            priceList
        ) { price ->
            val itemList = listOf<@Composable RowScope.() -> Unit> {
                Column {
                    Text(
                        text = price.title,
                        fontWeight = if (price.bold) FontWeight.Bold else FontWeight.Normal,
                        fontSize = fontSize,
                    )

                    if (price.subTitle != null)
                        Text(
                            text = price.subTitle,
                            fontSize = 12.sp,
                        )
                }

                //
                Text(
                    text = price.price,
                    fontSize = 12.sp,
                    fontWeight = if (price.bold) FontWeight.Bold else FontWeight.Light,
                )
            }

            SpaceBetweenRow(items = itemList)
        }

        item {
            VerticalSpace(space = SpaceBetweenViews)

            Text(
                text = "Note : cost can be change at the time of pickup depending upon the weight and volumetric weight of the items",
                color = primaryColor.invoke()
            )

            AppDivider(
                thickness = 1.dp, modifier = Modifier.padding(
                    horizontal = ExtraLowPadding
                )
            )

            RoundedCornerCard(
                modifier = Modifier
                    .padding(bottom = VeryLowSpacing),
                cardColor = cardContainerOnSecondaryColor.invoke(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(MediumPadding)
                        .fillMaxWidth(),
                ) {
                    Text(
                        "Select Delivery Method",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                    val radioOptions = listOf(
                        RadioButtonInfo(
                            id = 1,
                            text = "Express Delivery",
                        ),
                        RadioButtonInfo(
                            id = 2,
                            text = "Standard Delivery"
                        )
                    )

                    AppRadioButton(
                        radioOptions,
                        when (viewModel.deliveryType) {
                            DeliveryType.Express -> radioOptions[0].text
                            DeliveryType.Standard -> radioOptions[1].text
                        },
                        onOptionSelected = {
                            when (it.id) {
                                1 -> {
                                    viewModel.deliveryType = DeliveryType.Express
                                    viewModel.onEvent(GharKaKhanaEvent.BookNow)
                                }

                                2 -> {
                                    viewModel.deliveryType = DeliveryType.Standard
                                    viewModel.onEvent(GharKaKhanaEvent.BookNow)
                                }
                            }
                        },
                        fontSize = fontSize1,
                    )

                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                    Text(
                        "Delivery Date & Time",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    val items = listOf<@Composable RowScope.() -> Unit> {
                        TextInCircle(
                            text = data.deliveryDate?.toDate("dd-MM-yy") ?: "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = LowPadding), fontSize = fontSize
                        )

                        HorizontalSpace(space = LowSpacing)

                        TextInCircle(
                            text = data.deliveryTimeslot ?: "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = LowPadding), fontSize = fontSize
                        )
                    }
                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                    SpaceBetweenRow(items = items)

                    VerticalSpace(space = SpaceBetweenViews)
                }
            }

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            if (state.bookButtonLoader)
                ShowLoading()
            else
                AppButton(
                    text = "Confirm Booking"
                ) {
                    viewModel.onEvent(GharKaKhanaEvent.ConfirmCheckout)
                }
        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ConfirmBookingScreenPreview() {
    T2PCustomerAppTheme {
        //ConfirmBookingScreen()
    }
}