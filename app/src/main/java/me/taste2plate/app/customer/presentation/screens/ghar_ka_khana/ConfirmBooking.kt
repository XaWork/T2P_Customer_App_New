package me.taste2plate.app.customer.presentation.screens.ghar_ka_khana

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.forestGreen
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.utils.toDate

@Composable
fun ConfirmBookingScreen(
    viewModel: GharKaKhanaViewModel,
    moveToHomeScreen:() -> Unit,
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
                title = "Ghar ka khana"
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
                    /*append("Shipping Cost : \n")
                    append("First mile long distance 10km extra pickup charge : \n")
                    append("Last mile long distance 15km extra delivery charge : \n")
                    append("Sub total : \n")
                    append("CGST : \n")
                    append("IGST: \n")
                    append("SGST : \n")*/
                    append("Pickup Price: ${state.checkout!!.data.pickupPrice}\n")
                    append("Delivery Price: ${state.checkout!!.data.deliveryPrice}\n")
                }
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                ) {
                    append("Total Estimate Cost : ${state.checkout!!.data.totalPrice}")
                    append("\nTotal Weight : ${state.checkout!!.data.totalWeight}\n\n")
                }
                withStyle(
                    SpanStyle()
                ) {
                    append("Delivery Date : ${state.checkout!!.data.deliveryDate.toDate(format = "dd-MM-yy")}\n")
                    append("Delivery Time : ${state.checkout!!.data.deliveryTimeslot}\n\n")
                }
                withStyle(
                    SpanStyle()
                ) {
                    append("Pickup Distance : ${state.checkout!!.data.pickupDistance}Km\n")
                    append("Delivery Distance : ${state.checkout!!.data.deliveryDistance}km\n\n")
                }
                withStyle(
                    SpanStyle(
                        color = forestGreen.invoke()
                    )
                ) {
                    append(
                        if (viewModel.deliveryType == DeliveryType.Express) state.checkout?.expressRemarks
                            ?: "" else state.checkout?.normalRemarks
                            ?: ""
                    )
                }
            }

            Text(text = billingSummery)

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