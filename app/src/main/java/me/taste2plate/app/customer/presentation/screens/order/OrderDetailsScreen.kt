package me.taste2plate.app.customer.presentation.screens.order

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.presentation.screens.Product
import me.taste2plate.app.customer.presentation.theme.ExtraLowPadding
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.MediumSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RatingInfoRow
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun OrderDetailsScreen() {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "T2P-1341343"
            ) {}
        }
    ) {
        ContentOrderDetailsScreen()
    }
}

@Composable
fun ContentOrderDetailsScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = ScreenPadding)
    ) {
        items(2) {
            SingleOrderItem()
        }

        item {
            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))

            Text(
                text = "Delivery Date : 20-10-2024 (Night)",
                fontWeight = FontWeight.W500
            )

            VerticalSpace(space = SpaceBetweenViews)
        }

        //Price list
        val priceList = listOf(
            "Total Item Cost" to "$rupeeSign 900.00",
            "Shipping Charge" to "$rupeeSign 178.00",
            "Packaging Charge" to "$rupeeSign 178.00",
            "IGST" to "$rupeeSign 178.00"
        )

        items(priceList) {
            SpaceBetweenRow(
                modifier = Modifier.padding(vertical = LowPadding),
                item1 = {
                    Text(it.first, color = MaterialTheme.colorScheme.inverseSurface)
                },
                item2 = {
                    Text(text = it.second, color = MaterialTheme.colorScheme.inverseSurface)
                }
            )

        }

        item {
            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))

            SpaceBetweenRow(
                modifier = Modifier.padding(vertical = SpaceBetweenViewsAndSubViews),
                item1 = {
                    Text(
                        "Final Total",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                }, item2 = {
                    Text(
                        "$rupeeSign 234",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                })

            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))

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
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = annotatedString,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End
            )

            VerticalSpace(space = MediumSpacing)

            Text("Shipping To", fontSize = 20.sp)

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            RoundedCornerCard(
                modifier = Modifier
                    .fillMaxWidth(),
                cardColor = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary
                ), elevation = 4.dp
            ) {
                Text(
                    "Address 1\nCity\nState\nPincode",
                    modifier = Modifier
                        .padding(ScreenPadding)
                )
            }

            Divider(modifier = Modifier.padding(vertical = SpaceBetweenViews))

            AppButton(
                text = "Cancel Order"
            ) {}
        }
    }
}

@Composable
fun SingleOrderItem() {
    RoundedCornerCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = VeryLowSpacing),
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(ScreenPadding)
            ) {
                val product = Product()
                NetworkImage(
                    image = product.image, modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(MediumRoundedCorners)),
                    contentScale = ContentScale.Crop
                )

                HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider()

            RatingInfoRow(
                flatOff = "${rupeeSign}345 x 3",
                rating = "$rupeeSign 983",
                showIcon = false,
                modifier = Modifier.padding(ScreenPadding),
                weight = ""
            )
        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderDetailsScreenPreview() {
    T2PCustomerAppTheme {
        OrderDetailsScreen()
    }
}