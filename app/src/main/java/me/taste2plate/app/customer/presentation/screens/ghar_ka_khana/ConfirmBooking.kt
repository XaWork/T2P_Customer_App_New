package me.taste2plate.app.customer.presentation.screens.ghar_ka_khana

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.forestGreen
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar

@Composable
fun ConfirmBookingScreen() {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Ghar ka khana"
            ) {}
        }
    ) {
        ContentConfirmBooking()
    }
}

@Composable
fun ContentConfirmBooking() {
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
                    append("User name\n")
                }
                withStyle(SpanStyle()) {
                    append("Address\n\n")
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
                    append("User name\n")
                }
                withStyle(SpanStyle()) {
                    append("Address")
                }
            }

            Text(text = address)

            AppDivider(thickness = 2.dp)
        }

        items(4) {
            SingleFoodItem(buttonVisible = false)
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
        ConfirmBookingScreen()
    }
}