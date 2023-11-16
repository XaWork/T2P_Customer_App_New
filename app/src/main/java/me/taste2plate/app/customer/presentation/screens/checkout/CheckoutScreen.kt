package me.taste2plate.app.customer.presentation.screens.checkout

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.domain.mapper.toCommonForWishAndCartItem
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.screens.address.AddressBottomSheet
import me.taste2plate.app.customer.presentation.screens.cart.CheckOutViewModel
import me.taste2plate.app.customer.presentation.screens.cart.CheckoutEvents
import me.taste2plate.app.customer.presentation.screens.cart.SingleCartAndWishlistItem
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
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
import me.taste2plate.app.customer.presentation.widgets.RadioButtonInfo
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.TextInCircle
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: CheckOutViewModel,
    onNavigateToOrderConfirmScreen: () -> Unit,
    onNavigateToAddressListScreen: () -> Unit,
) {
    val state = viewModel.state
    var showAddressBottomSheet by remember {
        mutableStateOf(false)
    }
    var showCouponBottomSheet by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(state.defaultAddress == null) {
        viewModel.onEvent(CheckoutEvents.GetDefaultAddress)
    }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    //address bottom sheet
    if (showAddressBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showAddressBottomSheet = false
            },
            sheetState = sheetState
        ) {
            AddressBottomSheet(
                isLoading = false,
                addressList = state.addressList,
                onNavigateToAddressListScreen = {
                    showAddressBottomSheet = false
                    onNavigateToAddressListScreen()
                },
                setDefaultAddress = {
                    viewModel.onEvent(CheckoutEvents.SetDefaultAddress(it))
                    showAddressBottomSheet = false
                }
            )
        }
    }

    //coupon bottom sheet
    if (showCouponBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showCouponBottomSheet = false
            },
            sheetState = sheetState
        ) {
            CouponBottomSheet(
                state.couponList,
                applyCoupon = {
                    showCouponBottomSheet = false
                },
                onItemSelected = {
                    showCouponBottomSheet = false
                }
            )
        }
    }

    //date picker dialog
    val datePickerState = rememberDatePickerState()
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                TextButton(onClick = { showDatePickerDialog = false }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerDialog = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Checkout"
            ) {}
        },
    ) {
        CheckoutScreenContent(
            viewModel = viewModel,
            openAddressSheet = {
                viewModel.onEvent(CheckoutEvents.GetAddressList)
                showAddressBottomSheet = true
            },
            onNavigateToOrderConfirmScreen = onNavigateToOrderConfirmScreen,
            updateCart = { id, quantity ->
                viewModel.onEvent(CheckoutEvents.UpdateCart(id, quantity))
            },
            showCoupons = {
                viewModel.onEvent(CheckoutEvents.GetCoupons)
                showCouponBottomSheet = true
            },
            showDatePicker = {
                showDatePickerDialog = true
            }
        )
    }
}

@Composable
fun CheckoutScreenContent(
    viewModel: CheckOutViewModel,
    openAddressSheet: () -> Unit,
    updateCart: (productId: String, quantity: Int) -> Unit,
    onNavigateToOrderConfirmScreen: () -> Unit,
    showCoupons: () -> Unit,
    showDatePicker: () -> Unit,
) {
    val state = viewModel.state

    val priceList = listOf(
        PriceData(
            title = "Price",
            price = viewModel.price.toString()
        ),
        PriceData(
            title = "Delivery Charge",
            price = viewModel.deliveryCharge.toString()
        ),
        PriceData(
            title = "Packaging Fee",
            price = viewModel.packagingFee.toString()
        ),
        PriceData(
            title = "CGST",
            price = viewModel.cgst.toString()
        ),
        PriceData(
            title = "SGST",
            price = viewModel.sgst.toString()
        ),
        PriceData(
            title = "IGST",
            price = viewModel.igst.toString()
        ),
        PriceData(
            title = "Coupon Discount",
            price = viewModel.discount.toString()
        ),
        PriceData(
            title = "Subscription Discount",
            price = viewModel.subscriptionDiscount.toString()
        ),
        PriceData(
            title = "Wallet Discount",
            price = viewModel.walletDiscount.toString()
        ),
    )

    LazyColumn(
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
    ) {
        //user info
        item {
            if (state.defaultAddress != null && state.user != null)
                AddressBar(
                    address = state.defaultAddress,
                    user = state.user,
                    openAddressSheet = openAddressSheet
                )
        }

        //cart items
        val items = state.cart!!.result.map { it.toCommonForWishAndCartItem() }.toList()
        items(items) { item ->
            SingleCartAndWishlistItem(isWishList = false, item, updateCart = {
                updateCart(
                    item.id,
                    it
                )
            })
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

            CouponInfo {
                showCoupons()
            }

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
                    text = "${rupeeSign}${viewModel.totalPrice}",
                    color = primaryColor.invoke()
                )
            })

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            DeliveryInfo(
                date = viewModel.date,
                showDatePicker = { showDatePicker() }
            )

            VerticalSpace(space = SpaceBetweenViews)

            PaymentInfo()

            AppButton(
                text = "Continue"
            ) {
                onNavigateToOrderConfirmScreen()
            }
        }
    }
}

@Composable
fun AddressBar(
    address: AddressListModel.Result,
    user: User,
    openAddressSheet: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "${user.fullName} |  ${address.title}\n\n ${address.address}",
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
fun CouponInfo(
    showCoupons: () -> Unit
) {
    Column {
        AppDivider()

        AppOutlineButton(
            text = "Apply Coupon"
        ) {
            showCoupons()
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
fun DeliveryInfo(
    date: String,
    showDatePicker: () -> Unit
) {
    Column {
        HeadingText("Delivery Options")

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        val radioOptions = listOf(
            RadioButtonInfo(
                id = 1,
                text = "Express\nDelivery"
            ),
            RadioButtonInfo(
                id = 1,
                text = "Standard\nDelivery"
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
                text = date.ifEmpty { "Date" },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = LowPadding)
                    .clickable {
                        showDatePicker()
                    }
            )
        }) {
            TextInCircle(
                text = "Time",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = LowPadding)
                    .clickable { }
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
        //CheckoutScreen({})
    }
}