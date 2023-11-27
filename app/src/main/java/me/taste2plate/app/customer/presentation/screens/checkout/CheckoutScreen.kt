package me.taste2plate.app.customer.presentation.screens.checkout

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.domain.mapper.toCommonForWishAndCartItem
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.dialog.CustomDialog
import me.taste2plate.app.customer.presentation.dialog.PriceDialog
import me.taste2plate.app.customer.presentation.dialog.SettingDialogType
import me.taste2plate.app.customer.presentation.dialog.SettingInfoDialog
import me.taste2plate.app.customer.presentation.screens.address.AddressBottomSheet
import me.taste2plate.app.customer.presentation.screens.cart.CheckOutViewModel
import me.taste2plate.app.customer.presentation.screens.cart.CheckoutEvents
import me.taste2plate.app.customer.presentation.screens.cart.CheckoutState
import me.taste2plate.app.customer.presentation.screens.cart.SingleCartAndWishlistItem
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.LowSpacing
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
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
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.RadioButtonInfo
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.TextInCircle
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.showToast
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

val fontSize = 14.sp
val fontSize1 = 12.sp

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: CheckOutViewModel,
    onNavigateToOrderConfirmScreen: () -> Unit,
    onNavigateToAddressListScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    navigateBack: () -> Unit,
) {
    val state = viewModel.state
    val context = LocalContext.current
    var showAddressBottomSheet by remember {
        mutableStateOf(false)
    }
    var showCouponBottomSheet by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(state) {
        when {
            state.defaultAddress == null -> {
                viewModel.onEvent(CheckoutEvents.GetDefaultAddress)
            }

            state.isError && state.errorMessage != null -> {
                showToast(state.errorMessage)
                viewModel.onEvent(CheckoutEvents.UpdateState)

                if (!state.finish)
                    onNavigateToHomeScreen()
            }

            state.orderConfirmed -> {
                onNavigateToOrderConfirmScreen()
            }
        }
    }

    val sheetState = rememberModalBottomSheetState()

    //address bottom sheet
    if (showAddressBottomSheet) {
        ModalBottomSheet(
            containerColor = screenBackgroundColor.invoke(),
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
            containerColor = screenBackgroundColor.invoke(),
            onDismissRequest = {
                showCouponBottomSheet = false
            },
            sheetState = sheetState
        ) {
            CouponBottomSheet(
                state.couponList,
                applyCoupon = {
                    showCouponBottomSheet = false
                    viewModel.onEvent(CheckoutEvents.ApplyCoupon(it))
                },
                onItemSelected = {
                    showCouponBottomSheet = false
                    if (state.couponList.isNotEmpty()) {
                        val code = state.couponList[it].coupon
                        viewModel.onEvent(CheckoutEvents.ApplyCoupon(code))
                    }
                }
            )
        }
    }

    if (viewModel.showDialogExpress) {
        SettingInfoDialog(
            checkAvailability = state.cutOffTimeCheckModel,
            setting = state.settings!!,
            type = SettingDialogType.Express_Delivery,
            setDate = { date, time ->
                viewModel.selectedDate = date
                viewModel.selectedTimeSlot = time
            },
            onDismissRequest = {
                viewModel.showDialogExpress = false
            },
            onConfirmation = {
                viewModel.showDialogExpress = false
            }
        )
    }

    if (viewModel.showDigitalCODDialog) {
        SettingInfoDialog(
            setting = state.settings!!,
            type = SettingDialogType.COD_Digital,
            onDismissRequest = {
                viewModel.showDigitalCODDialog = false
            },
            onConfirmation = {
                viewModel.showDigitalCODDialog = false
            }
        )
    }

    if (viewModel.showCustomDialog) {
        CustomDialog(title = "Alert!", text = viewModel.customDialogMessage) {
            viewModel.showCustomDialog = false
        }
    }

    //timeslot bottom sheet
    var showTimeSlotBottomSheet by remember {
        mutableStateOf(false)
    }
    if (showTimeSlotBottomSheet) {
        ModalBottomSheet(
            containerColor = screenBackgroundColor.invoke(),
            onDismissRequest = {
                showTimeSlotBottomSheet = false
            },
            sheetState = sheetState
        ) {
            TimePickerBottomSheet(
                state.timeSlots,
                onItemSelected = {
                    viewModel.selectedTimeSlot = state.timeSlots[it]
                    showTimeSlotBottomSheet = false
                }
            )
        }
    }

    if (state.myPlan != null) {
        PriceDialog(
            minOrder = viewModel.minOrder,
            walletPoint = viewModel.walletPoint,
            onConfirmation = {
                viewModel.onEvent(CheckoutEvents.ChangeMyPlanValue)
            }
        )
    }

    //date picker dialog
    val todayDate = Instant.now().toEpochMilli()
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = todayDate)
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    if (showDatePickerDialog) {
        ModalBottomSheet(
            onDismissRequest = {
                showDatePickerDialog = false
            },
            sheetState = sheetState
        ) {
            DatePicker(state = datePickerState, dateValidator = {
                val instant = Instant.ofEpochMilli(todayDate)
                val newInstant = instant.minus(1, ChronoUnit.DAYS)
                it >= newInstant.toEpochMilli()
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
                    viewModel.selectedDate =
                        selectedDate?.format(DateTimeFormatter.ISO_LOCAL_DATE).toString()
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

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Checkout"
            ) {
                navigateBack()
            }
        },
    ) {
        CheckoutScreenContent(
            viewModel = viewModel,
            openAddressSheet = {
                viewModel.onEvent(CheckoutEvents.GetAddressList)
                showAddressBottomSheet = true
            },
            onNavigateToOrderConfirmScreen = {
                viewModel.onEvent(CheckoutEvents.Checkout(context))
            },
            updateCart = { id, quantity ->
                viewModel.onEvent(CheckoutEvents.UpdateCart(id, quantity))
            },
            showCoupons = {
                showCouponBottomSheet = true
            },
            showDatePicker = {
                showDatePickerDialog = true
            },
            showTimeSlots = {
                if (state.deliveryType == DeliveryType.Standard)
                    showTimeSlotBottomSheet = true
            },
            changeDeliveryType = {
                viewModel.onEvent(CheckoutEvents.ChangeDeliveryType(it))
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
    showTimeSlots: () -> Unit,
    changeDeliveryType: (DeliveryType) -> Unit,
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
            price = viewModel.couponDiscount.toString()
        ),
        PriceData(
            title = "Subscription Discount",
            price = viewModel.subscriptionDiscount.toString()
        ),
        PriceData(
            title = "Wallet Discount",
            price = viewModel.discountWallet.toString(),
            subTitle = viewModel.pointConversionText.ifEmpty { null }
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
            SingleCartAndWishlistItem(
                isWishList = false, item,
                updateCart = {
                    updateCart(
                        item.id,
                        it
                    )
                },
                fontSize = fontSize,
            )
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


            CouponInfo(
                state = state
            ) {
                showCoupons()
            }

            TipInfo(
                state = state
            ) { index ->
                viewModel.onEvent(CheckoutEvents.UpdateTip(index))
            }

            CancellationPolicy()

            HeadingText("Price Details")

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)
        }

        items(
            priceList
        ) { price ->
            val itemList = listOf<@Composable RowScope.() -> Unit> {
                Column {
                    Text(
                        text = price.title,
                        fontWeight = FontWeight.Light, fontSize = fontSize
                    )
                    if (price.subTitle != null)
                        Text(
                            text = price.subTitle,
                            fontSize = 12.sp,
                        )
                }

                Text(
                    text = "${price.sign}${price.price}", fontSize = fontSize
                )
            }

            SpaceBetweenRow(items = itemList)
        }

        item {
            if (viewModel.walletEnabled)
                AppCheckBox(
                    checked = viewModel.walletChecked,
                    onCheckedChange = { viewModel.onEvent(CheckoutEvents.ChangeWalletCheckStatus) },
                    text = "Check to use wallet discount"
                )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            SpaceBetweenRow(item1 = {
                Text(
                    text = "Total Price",
                    color = primaryColor.invoke(), fontSize = fontSize
                )

            }, item2 = {
                Text(
                    text = "${rupeeSign}${viewModel.totalPrice}",
                    color = primaryColor.invoke(), fontSize = fontSize
                )
            })

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            DeliveryInfo(
                viewModel = viewModel,
                date = viewModel.selectedDate,
                timeSlot = viewModel.selectedTimeSlot,
                deliveryType = state.deliveryType,
                showDatePicker = { showDatePicker() },
                showTimeSlots = { showTimeSlots() },
                changeDeliveryType = changeDeliveryType
            )

            VerticalSpace(space = SpaceBetweenViews)

            PaymentInfo(viewModel, changePaymentType = {
                viewModel.onEvent(CheckoutEvents.ChangePaymentType(it))
            })

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            if (state.buttonLoading)
                ShowLoading()
            else
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
            text = "${user.fullName} | ${address.title}\n${address.address}\n${address.address2}\n${address.city.name}, ${address.state.name}\n${address.pincode}",
            fontWeight = FontWeight.Light,
            fontSize = fontSize
        )

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        AppOutlineButton(
            text = "change or add new address".uppercase(),
            fontSize = fontSize1
        ) {
            openAddressSheet()
        }

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        AppDivider()
    }
}

@Composable
fun CouponInfo(
    state: CheckoutState,
    showCoupons: () -> Unit
) {
    Column {
        AppDivider()

        AppOutlineButton(
            text = "Apply Coupon",
            fontSize = fontSize1
        ) {
            showCoupons()
        }

        if (state.applyCouponResponse != null) {
            val items = listOf<@Composable RowScope.() -> Unit> {
                Text(
                    text = state.applyCouponResponse.coupon_type,
                    color = primaryColor.invoke()
                )

                MaterialIcon(
                    imageVector = Icons.Outlined.Cancel
                )
            }

            VerticalSpace(space = VeryLowSpacing)

            SpaceBetweenRow(items = items)
        }

        AppDivider()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipInfo(
    state: CheckoutState,
    onTipSelect: (index: Int) -> Unit
) {
    val items = listOf<@Composable RowScope.() -> Unit> {
        val aboutTip = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Tip your delivery partner\n")
            }

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Light,
                    fontSize = fontSize
                )
            ) {
                append("Thanks for your kindness! 100% of your tip will go directly to Delivery Associate.")
            }
        }

        Text(
            aboutTip,
            modifier = Modifier
                .align(Alignment.Top)
                .fillMaxWidth()
                .weight(3f)
        )

        DrawableImage(
            id = R.drawable.delivery_man,
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.Bottom)
                .fillMaxWidth()
                .weight(1f)
        )
    }

    Column {
        SpaceBetweenRow(items = items)

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
        ) {
            val tips = state.tips
            itemsIndexed(tips) { index, tip ->
                FilterChip(
                    selected = tip.selected,
                    shape = RoundedCornerShape(LowRoundedCorners),
                    border = FilterChipDefaults.filterChipBorder(selectedBorderColor = primaryColor.invoke()),
                    onClick = {
                        onTipSelect(index)
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

                            /* val text = if (tips[index].tipPrice != "Other")
                                 "$rupeeSign${tips[index].tipPrice}"
                             else tips[index].tipPrice*/

                            Text(
                                text = "$rupeeSign${tips[index].tipPrice}",
                                fontSize = fontSize1,
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
fun CancellationPolicy(
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Distance Traveled".uppercase(),
            letterSpacing = 5.sp,
            modifier = Modifier.padding(vertical = LowPadding)
        )

        Text(
            text = "Your food will travel 1212kms by Air to reach Fresh to you :)",
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )

        VerticalSpace(space = LowSpacing)

        Text(
            text = "Freshness Assured".uppercase(),
            letterSpacing = 5.sp,
            modifier = Modifier.padding(vertical = LowPadding)
        )

        Text(
            text = "We assured same freshness for the Delivered food as it was packed. Please check on Delivery and help",
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )

        VerticalSpace(space = LowSpacing)

        Text(
            text = "Order cancellation".uppercase(),
            letterSpacing = 5.sp,
            modifier = Modifier.padding(vertical = LowPadding)
        )

        Text(
            text = "Order can be cancelled only up-to 4 hours of order confirmation or before the cutoff time, whichever will be earlier.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )

        AppDivider()
    }
}

@Composable
fun DeliveryInfo(
    viewModel: CheckOutViewModel,
    date: String,
    timeSlot: String,
    deliveryType: DeliveryType,
    changeDeliveryType: (DeliveryType) -> Unit,
    showDatePicker: () -> Unit,
    showTimeSlots: () -> Unit
) {
    val state = viewModel.state
    Column {
        HeadingText("Delivery Options")

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        val radioOptions = listOf(
            RadioButtonInfo(
                id = 1,
                text = "Express\nDelivery",
                enable = viewModel.expressEnabled
            ),
            RadioButtonInfo(
                id = 2,
                text = "Standard\nDelivery"
            )
        )

        AppRadioButton(
            radioOptions,
            if (deliveryType == DeliveryType.Express) radioOptions[0].text else radioOptions[1].text,
            onOptionSelected = {
                when (it.id) {
                    1 -> {
                        changeDeliveryType(DeliveryType.Express)
                    }

                    2 -> {
                        changeDeliveryType(DeliveryType.Standard)
                    }
                }
            },
            fontSize = fontSize,
        )

        VerticalSpace(space = SpaceBetweenViews)

        if (state.cutOffTimeCheckModel != null)
            Text(
                if (state.deliveryType == DeliveryType.Standard) state.cutOffTimeCheckModel.result.remarks
                else state.cutOffTimeCheckModel.result.expressRemarks,
                color = primaryColor.invoke()
            )

        VerticalSpace(space = SpaceBetweenViews)

        Text(
            text = "Delivery Info",
            color = primaryColor.invoke(), fontSize = fontSize
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
                    .noRippleClickable {
                        showDatePicker()
                    }, fontSize = fontSize
            )
        }) {
            TextInCircle(
                text = timeSlot.ifEmpty { "Time" },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = LowPadding)
                    .noRippleClickable { showTimeSlots() }, fontSize = fontSize
            )
        }
    }
}

@Composable
fun PaymentInfo(
    viewModel: CheckOutViewModel,
    changePaymentType: (PaymentType) -> Unit
) {
    val state = viewModel.state
    val radioOptions = listOf(
        RadioButtonInfo(
            id = 1,
            text = "Online",
        ),
        RadioButtonInfo(
            id = 2,
            text = "COD",
            isIcon = false,
            enable = viewModel.codEnabled
        )
    )
    val selectedOption =
        if (state.paymentType == PaymentType.Online) radioOptions[0].text else radioOptions[1].text

    Column {
        HeadingText("Payment")

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        AppRadioButton(
            radioOptions,
            selectedOption,
            onOptionSelected = {
                when (it.id) {
                    1 -> changePaymentType(PaymentType.Online)
                    2 -> changePaymentType(PaymentType.COD)
                }
            },
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