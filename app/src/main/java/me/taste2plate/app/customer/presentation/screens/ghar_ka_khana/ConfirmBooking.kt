package me.taste2plate.app.customer.presentation.screens.ghar_ka_khana

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryType
import me.taste2plate.app.customer.presentation.screens.checkout.fontSize
import me.taste2plate.app.customer.presentation.screens.checkout.fontSize1
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
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
            val address = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                ) {
                    append("Pickup Location\n")
                }
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                ) {
                    append("${state.user?.fullName}\n${state.user?.mobile}\n")
                }
                withStyle(SpanStyle()) {
                    append("${state.pickupLocation?.address}, ${state.pickupLocation?.city?.name}, ${state.pickupLocation?.state?.name} - ${state.pickupLocation?.pincode}\n\n")
                }
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                ) {
                    append("Destination Location\n")
                }
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                ) {
                    append("${state.user?.fullName}\n${state.user?.mobile}\n")
                }
                withStyle(SpanStyle()) {
                    append("${state.destinationLocation?.address}, ${state.destinationLocation?.city?.name}, ${state.destinationLocation?.state?.name} - ${state.destinationLocation?.pincode}\n\n")
                }
            }

            Text(text = address)

            AppDivider(thickness = 2.dp)
        }

        items(state.cartItems) {
            SingleFoodItem(it,
                buttonVisible = false,
                onDelete = {
                    viewModel.onEvent(GharKaKhanaEvent.DeleteCart(it.id))
                })
        }

        item {
            AppDivider(thickness = 2.dp)

            var data = state.checkout!!
            val billingSummery = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                ) {
                    append("Billing Summery\n")
                }
                withStyle(SpanStyle()) {
                    /*append("Shipping Cost : ${data.}\n")
                    append("First mile long distance 10km extra pickup charge : \n")
                    append("Last mile long distance 15km extra delivery charge : \n")*/
                    append("CGST : ${rupeeSign}${data.cgst?.toDecimal()}\n")
                    append("IGST: ${rupeeSign}${data.igst?.toDecimal()}\n")
                    append("SGST : ${rupeeSign}${data.sgst?.toDecimal()}\n")
                    append("Pickup Price: ${rupeeSign}${data.pickupPrice?.toDecimal()}\n")
                    append("Delivery Price: ${rupeeSign}${data.deliveryPrice?.toDecimal()}\n")
                }
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                ) {
                    append("Total Estimate Cost : ${rupeeSign}${data.totalPrice?.toDecimal()}\n")
                    append("\nTotal Weight : ${data.totalWeight?.toDecimal()}\n\n")
                }
                /* withStyle(
                     SpanStyle()
                 ) {
                     append("Delivery Date : ${state.checkout!!.data.deliveryDate.toDate(format = "dd-MM-yy")}\n")
                     append("Delivery Time : ${state.checkout!!.data.deliveryTimeslot}\n\n")
                 }*/
                withStyle(
                    SpanStyle()
                ) {
                    append("Pickup Distance : ${data.pickupDistance?.toDecimal()}Km\n")
                    append("Delivery Distance : ${data.deliveryDistance?.toDecimal()}km\n\n")
                }
                /*  withStyle(
                      SpanStyle(
                          color = forestGreen.invoke()
                      )
                  ) {
                      append(
                          if (viewModel.deliveryType == DeliveryType.Express) state.checkout?.expressRemarks
                              ?: "" else state.checkout?.normalRemarks
                              ?: ""
                      )
                  }*/
            }

            Text(text = billingSummery)


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
                    text = data.deliveryDate?.toDate("dd-mm-yy") ?: "",
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

            Text(
                text = "Note : cost can be change at the time of pickup depending upon the weight and volumetric weight of the items",
                color = primaryColor.invoke()
            )

            VerticalSpace(space = SpaceBetweenViews)

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