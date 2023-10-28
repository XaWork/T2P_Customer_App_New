package me.taste2plate.app.customer.presentation.screens.checkout

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.address.AddressBottomSheet
import me.taste2plate.app.customer.presentation.screens.productList
import me.taste2plate.app.customer.presentation.theme.ExtraLowPadding
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppCheckBox
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppOutlineButton
import me.taste2plate.app.customer.presentation.widgets.AppRadioButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.HeadingText
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RadioButtonInfo
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.TextInCircle
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen() {
    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    AppScaffold(
        topBar = {
            AppTopBar {}
        },
    ) {
        CheckoutScreenContent(
            openAddressSheet = {
                showBottomSheet = true
            }
        )

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                AddressBottomSheet()
            }
        }
    }
}

@Composable
fun CheckoutScreenContent(
    openAddressSheet: () -> Unit
) {
    val priceList = listOf(
        PriceData(
            title = "Price",
            price = "1300"
        ),
        PriceData(
            title = "Delivery Charge",
            price = "130"
        ),
        PriceData(
            title = "Packaging Fee",
            price = "10"
        ),
        PriceData(
            title = "CGST",
            price = "1300"
        ),
        PriceData(
            title = "SGST",
            price = "1300"
        ),
        PriceData(
            title = "IGST",
            price = "1300"
        ),
        PriceData(
            title = "Discount",
            price = "1300"
        ),
        PriceData(
            title = "Subscription Discount",
            price = "1300"
        ),
        PriceData(
            title = "Wallet Discount",
            price = "1300"
        ),
    )

    LazyColumn(
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
    ) {
        item {
            AddressBar(
                openAddressSheet = openAddressSheet
            )
        }

        items(2) {
            SingleCartItem()
        }

        item {
            InfoWithIcon(
                id = R.drawable.party,
                info = "You Saved $rupeeSign on this order",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue.copy(alpha = 0.1f))
                    .padding(MediumPadding),
                textColor = Color.Blue,
                iconOrImageModifier = Modifier
                    .size(30.dp)

            )

            CouponInfo()

            TipInfo({})

            HeadingText("Price Details")

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)
        }

        items(
            priceList
        ) { price ->
            val items = listOf<@Composable RowScope.() -> Unit> {
                Text(
                    text = price.title,
                    fontWeight = FontWeight.Light
                )

                Text(
                    text = "${price.sign}${price.price}"
                )
            }

            SpaceBetweenRow(items = items)
        }

        item {
            AppCheckBox(
                onCheckedChange = { /*TODO*/ },
                text = "Check to use wallet discount"
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            SpaceBetweenRow(item1 = {
                Text(
                    text = "Total Price",
                    color = primaryColor.invoke()
                )

            }, item2 = {
                Text(
                    text = "${rupeeSign}1819",
                    color = primaryColor.invoke()
                )
            })

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            DeliveryInfo()

            VerticalSpace(space = SpaceBetweenViews)

            PaymentInfo()

            AppButton(
                text = "Continue"
            ) {

            }
        }
    }
}

@Composable
fun AddressBar(
    openAddressSheet: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Xa Kaler |  Home\n\n Address here",
            fontWeight = FontWeight.Light
        )

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        AppOutlineButton(
            text = "change or add new address".uppercase(),
        ) {
            openAddressSheet()
        }

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        AppDivider()
    }
}


@Composable
fun SingleCartItem() {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = LowElevation
    ) {
        var items = listOf<@Composable RowScope.() -> Unit> {
            NetworkImage(
                image = productList[0].image,
                modifier = Modifier
                    .clip(RoundedCornerShape(MediumRoundedCorners))
                    .size(70.dp)
                    .weight(1f),
                contentScale = ContentScale.Crop
            )

            val productInfo = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.W400
                    )
                ) {
                    append("Product name")
                }

                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Light,
                        color = primaryColor.invoke()
                    )
                ) {
                    append("\n${rupeeSign}238")
                }
            }

            Text(
                text = productInfo,
                maxLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceBetweenViewsAndSubViews)
                    .weight(3f)
            )

            Text(
                "2",
                modifier = Modifier
                    .background(primaryColor.invoke())
                    .padding(MediumPadding),
                color = backgroundColor.invoke()
            )
        }

        SpaceBetweenRow(
            items = items,
            modifier = Modifier.padding(MediumPadding)
        )
    }
}

@Composable
fun CouponInfo() {
    Column {
        AppDivider()

        AppOutlineButton(
            text = "Apply Coupon"
        ) {
        }

        AppDivider()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipInfo(
    onTipSelect: () -> Unit
) {
    val items = listOf<@Composable RowScope.() -> Unit> {
        val aboutTip = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Tip your delivery partner")
            }

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Light
                )
            ) {
                append("\nYour kindness a lot! 100% of\n your tip will go directly to them")
            }
        }

        Text(
            aboutTip,
            modifier = Modifier
                .align(Alignment.Top)
        )

        DrawableImage(
            id = R.drawable.delivery_man,
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.Bottom)
        )
    }

    var tips = remember {
        mutableListOf(
            TipData(tipPrice = "10"),
            TipData(tipPrice = "20", mostTipped = true),
            TipData(tipPrice = "30"),
            TipData(tipPrice = "Other"),
        )
    }

    Column {
        SpaceBetweenRow(items = items)

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
        ) {
            itemsIndexed(tips) { index, tip ->

                FilterChip(
                    selected = tip.selected,
                    shape = RoundedCornerShape(LowRoundedCorners),

                    onClick = {
                        if (tips[index].tipPrice != "Other")
                            tips[index].selected = !tips[index].selected
                        //TODo : show text field of product details page (which we use in pincode)
                    }, label = {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (tip.mostTipped)
                                Text(
                                    text = "Most Tipped",
                                    color = primaryColor.invoke(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center
                                )

                            Text(
                                text = "$rupeeSign${tips[index].tipPrice}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    })
            }
        }

        AppDivider()

    }

}

@Composable
fun DeliveryInfo() {
    Column {
        HeadingText("Delivery Options")

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        val radioOptions = listOf(
            RadioButtonInfo(
                id = 1,
                text = "Express Delivery"
            ),
            RadioButtonInfo(
                id = 1,
                text = "Standard Delivery"
            )
        )
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
        AppRadioButton(
            radioOptions,
            selectedOption.text,
            onOptionSelected
        )

        VerticalSpace(space = SpaceBetweenViews)

        Text(
            text = "Delivery Info",
            color = primaryColor.invoke()
        )

        VerticalSpace(space = SpaceBetweenViews)

        HeadingText("Delivery Date")

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        SpaceBetweenRow(item1 = {
            TextInCircle(
                text = "Date",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = LowPadding)
            )
        }) {
            TextInCircle(
                text = "Time",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = LowPadding)
            )
        }
    }
}

@Composable
fun PaymentInfo() {
    val radioOptions = listOf(
        RadioButtonInfo(
            id = 1,
            text = "Online",
            isIcon = true,
            icon = R.drawable.online_payment
        ),
        RadioButtonInfo(
            id = 1,
            text = "COD",
            isIcon = true,
            icon = R.drawable.cod_icon
        )
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column {
        HeadingText("Payment")

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        AppRadioButton(
            radioOptions,
            selectedOption.text,
            onOptionSelected,
            isIcon = true
        )
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CheckoutScreenPreview() {
    T2PCustomerAppTheme {
        CheckoutScreen()
    }
}