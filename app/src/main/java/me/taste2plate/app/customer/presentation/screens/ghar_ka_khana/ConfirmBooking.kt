package me.taste2plate.app.customer.presentation.screens.ghar_ka_khana

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.presentation.dialog.TimePickerDialog
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryInfo
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ConfirmBookingScreen(
    viewModel: GharKaKhanaViewModel,
    navigateBack: () -> Unit,

    ) {

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Ghar ka khana"
            ) {}
        }
    ) {
        if (viewModel.state.isLoading)
            ShowLoading(isButton = false)
        else
            ContentConfirmBooking(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentConfirmBooking(
    viewModel: GharKaKhanaViewModel,
) {
    val state = viewModel.state


    var showDatePickerDialog by remember { mutableStateOf(false) }

    val simpleDateFormatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    val startFromDate =
        simpleDateFormatter.parse("")!!.time
    val datePickerState =
        rememberDatePickerState()
    if (showDatePickerDialog) {
        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                containerColor = screenBackgroundColor.invoke()
            ),
            onDismissRequest = { showDatePickerDialog = false }, confirmButton = {
            }) {
            DatePicker(state = datePickerState, dateValidator = {
                val instant = Instant.ofEpochMilli(startFromDate)
                it >= instant.toEpochMilli()
            })

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ScreenPadding)
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    val selectedDate = datePickerState.selectedDateMillis?.let {
                        Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
                    }
                    if (selectedDate != null)
                        viewModel.selectedDate =
                            selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE).toString()
                    showDatePickerDialog = false
                }) {
                    Text("OK")
                }

                HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

                TextButton(onClick = {
                    showDatePickerDialog = false
                }) {
                    Text("CANCEL")
                }
            }

            AppDivider()
        }
    }

    var showTimeSlotDialog by remember {
        mutableStateOf(false)
    }
    if (showTimeSlotDialog) {
        TimePickerDialog(viewModel.pickupTimeSlots,
            onDismiss = {
                showTimeSlotDialog = false
            },
            onItemSelected = {
                viewModel.selectedTimeSlot = viewModel.pickupTimeSlots[it]
                showTimeSlotDialog = false
            }
        )
    }

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
                    append("Shipping Cost : \n")
                    append("First mile long distance 10km extra pickup charge : \n")
                    append("Last mile long distance 15km extra delivery charge : \n")
                    append("Sub total : \n")
                    append("CGST : \n")
                    append("IGST: \n")
                    append("SGST : \n")
                }
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                ) {
                    append("Total Estimate Cost : ")
                    append("\nTotal Weight : \n")
                }
            }

            Text(text = billingSummery)

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

           /* DeliveryInfo(
                cutOffTimeCheckModel = state.cutOffTimeCheckModel,
                date = viewModel.selectedDate,
                timeSlot = viewModel.selectedTimeSlot,
                deliveryType = state.deliveryType,
                showDatePicker = {  },
                showTimeSlots = { showTimeSlots() },
                changeDeliveryType = changeDeliveryType,
                expressEnabled = viewModel.expressEnabled
            )*/

            AppButton(
                text = "Confirm Booking"
            ) {

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