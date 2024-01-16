package me.taste2plate.app.customer.presentation.screens.ghar_ka_khana

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaFetchCartModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.dialog.TimePickerDialog
import me.taste2plate.app.customer.presentation.screens.address.AddressBottomSheet
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryType
import me.taste2plate.app.customer.presentation.screens.checkout.fontSize
import me.taste2plate.app.customer.presentation.screens.checkout.fontSize1
import me.taste2plate.app.customer.presentation.theme.ExtraLowElevation
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowSpacing
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.forestGreen
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppCheckBox
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppDropDown
import me.taste2plate.app.customer.presentation.widgets.AppRadioButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.RadioButtonInfo
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.TextInCircle
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.showToast
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun BookingScreen(
    viewModel: GharKaKhanaViewModel,
    onNavigateToAddressListScreen: () -> Unit,
    onNavigateToCheckoutScreen: () -> Unit,
    navigateBack: () -> Unit,
) {
    val state = viewModel.state
    LaunchedEffect(state) {
        //Log.e("Booking screen", "Error : ${state.isError} and message : ${state.message}")
        if (state.isError && state.message != null) {
            showToast(state.message!!)
            viewModel.onEvent(GharKaKhanaEvent.UpdateState)
        }
        if (state.moveToCheckout) {
            onNavigateToCheckoutScreen()
            viewModel.onEvent(GharKaKhanaEvent.UpdateState)
        }
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Any Food 2 Any Place"
            ) { navigateBack() }
        }
    ) {
        if (state.isLoading)
            ShowLoading()
        else
            ContentBookingScreen(
                viewModel, onNavigateToAddressListScreen, onNavigateToCheckoutScreen
            )
    }
}

@Composable
fun ContentBookingScreen(
    viewModel: GharKaKhanaViewModel,
    onNavigateToAddressListScreen: () -> Unit,
    onNavigateToCheckoutScreen: () -> Unit,
) {
    val state = viewModel.state
    LazyColumn(
        modifier = Modifier.padding(ScreenPadding)
    ) {
        item {
            PdLocation(viewModel, onNavigateToAddressListScreen = onNavigateToAddressListScreen)

            AppDivider(thickness = 2.dp)

            FoodInfo(viewModel)

            AppDivider(thickness = 2.dp)
        }

        items(state.cartItems) {
            SingleFoodItem(it,
                buttonVisible = !state.buttonLoader,
                onDelete = {
                    viewModel.onEvent(GharKaKhanaEvent.DeleteCart(it.id))
                })
        }

        item {
            AppDivider(thickness = 2.dp)

            GKKPickupInfo(viewModel)

            if (state.bookButtonLoader)
                ShowLoading()
            else
                AppButton(text = "Book Now") {
                    viewModel.onEvent(GharKaKhanaEvent.BookNow)

                   /* if (state.pickupLocation == null || state.destinationLocation == null) {
                        showToast("Choose Pickup and Destination Location.")
                    } else if (state.cartItems.isEmpty()) {
                        showToast("Add at least one item.")
                    } else if (viewModel.selectedDate.isEmpty() || viewModel.selectedTimeSlot.isEmpty()) {
                        showToast("Select pickup date and time.")
                    } else {
                        onNavigateToCheckoutScreen()
                    }*/
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdLocation(
    viewModel: GharKaKhanaViewModel,
    onNavigateToAddressListScreen: () -> Unit,
) {
    val state = viewModel.state
    var pdLocation by remember {
        mutableStateOf(PDLocation.Destination)
    }
    var showBottomSheet by remember { mutableStateOf(false) }
    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = screenBackgroundColor.invoke(),
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = rememberModalBottomSheetState()
        ) {
            AddressBottomSheet(
                isLoading = state.addressLoader,
                state.addressListModel,
                defaultAddress = null,
                setDefaultAddress = {
                    showBottomSheet = false
                    viewModel.onEvent(
                        GharKaKhanaEvent.SetPDLocation(
                            location = it,
                            pdLocationType = pdLocation
                        )
                    )
                },
                onNavigateToAddressListScreen = {
                    showBottomSheet = false
                    onNavigateToAddressListScreen()
                }
            )
        }
    }
    SinglePdLocation(
        backgroundColor = primaryColor.invoke(),
        title = "Pickup Location",
        iconId = R.drawable.pickuplocation_icon,
        address = state.pickupLocation,
        onClick = {
            pdLocation = PDLocation.Pickup
            showBottomSheet = true
            viewModel.onEvent(GharKaKhanaEvent.GetAddress)
        }
    )

    VerticalSpace(space = SpaceBetweenViewsAndSubViews)

    SinglePdLocation(
        backgroundColor = forestGreen.invoke(),
        title = "Destination Location",
        iconId = R.drawable.destination_icon,
        address = state.destinationLocation,
        onClick = {
            pdLocation = PDLocation.Destination
            showBottomSheet = true
            viewModel.onEvent(GharKaKhanaEvent.GetAddress)
        }
    )
}

@Composable
fun SinglePdLocation(
    backgroundColor: Color,
    title: String = "",
    iconId: Int,
    address: AddressListModel.Result?,
    onClick: () -> Unit
) {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = ExtraLowElevation
    ) {
        val items = listOf<@Composable RowScope.() -> Unit> {
            InfoWithIcon(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = backgroundColor
                    )
                    .padding(vertical = ScreenPadding, horizontal = MediumPadding)
                    .weight(1f)
                    .clickable { onClick() },
                iconOrImageModifier = Modifier.size(50.dp),
                id = iconId,
                info = title.uppercase(),
                colorFilter = ColorFilter.tint(screenBackgroundColor.invoke()),
                textColor = screenBackgroundColor.invoke(),
                maxLines = 2,
                fontSize = 14.sp,

                )

            Text(
                if (address != null) "${address.address}, ${address.city.name}, ${address.state.name} - ${address.pincode}" else "",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = MediumPadding),
            )
        }

        SpaceBetweenRow(items = items)
    }
}

@Composable
fun FoodInfo(viewModel: GharKaKhanaViewModel) {
    val state = viewModel.state
    var categoryExpanded by remember { mutableStateOf(false) }
    var subCategoryExpanded by remember { mutableStateOf(false) }
    val items = listOf<@Composable RowScope.() -> Unit> {
        AppDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            expanded = categoryExpanded,
            hint = "Category",
            items = state.category.map { it.name },
            onExpandedChange = {
                categoryExpanded = !categoryExpanded
            },
            selectedText = viewModel.category,
            onTextChanged = {
                viewModel.category = it
            })

        HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

        AppDropDown(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            hint = "Sub Category",
            expanded = subCategoryExpanded,
            items = state.subCategory.map { it.name },
            onExpandedChange = {
                subCategoryExpanded = !subCategoryExpanded
            },
            selectedText = viewModel.subCategory,
            onTextChanged = {
                viewModel.subCategory = it
            })
    }

    Column {
        SpaceBetweenRow(items = items)

        AppTextField(
            value = viewModel.productName,
            onValueChanged = {
                viewModel.productName = it
            },
            hint = "Product Name"
        )

        val rowItems = listOf<@Composable RowScope.() -> Unit> {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = viewModel.weight,
                keyboardType = KeyboardType.Number,
                onValueChanged = {
                    viewModel.weight = it
                },
                hint = "Weight(Kg)"
            )

            HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

            if (state.buttonLoader)
                ShowLoading(
                    boxModifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            else
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    buttonColors = ButtonDefaults.buttonColors(
                        containerColor = forestGreen.invoke()
                    ),
                    text = "Add"
                ) {
                    viewModel.onEvent(GharKaKhanaEvent.AddToCart)
                }
        }

        SpaceBetweenRow(items = rowItems)
    }

}

@Composable
fun SingleFoodItem(
    item: GharKaKhanaFetchCartModel.Result,
    buttonVisible: Boolean = true,
    onDelete: () -> Unit = {}
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
                item.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            val items = listOf<@Composable RowScope.() -> Unit> {
                Text("Weight : ${item.weight} kg")

                if (buttonVisible)
                    Row {
                        /*AppOutlineButton(
                            modifier = Modifier
                                .height(35.dp)
                                .width(80.dp),
                            text = "Edit",
                            shape = CircleShape
                        ) {}

                        HorizontalSpace(space = VeryLowSpacing)*/

                        AppButton(
                            modifier = Modifier
                                .height(35.dp)
                                .width(100.dp),
                            text = "Delete",
                            shape = CircleShape
                        ) { onDelete() }
                    }
            }

            SpaceBetweenRow(items = items)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GKKPickupInfo(viewModel: GharKaKhanaViewModel) {
    val state = viewModel.state
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    var timePickerExpanded by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    if (showDatePickerDialog) {
        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                containerColor = screenBackgroundColor.invoke()
            ),
            onDismissRequest = { showDatePickerDialog = false }, confirmButton = {
            }) {
            DatePicker(state = datePickerState, dateValidator = {
                val instant = Instant.now()
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

    Column {
        var weight = 0.0f
        state.cartItems.forEach { weight += it.weight.toFloat() }
        AppTextField(
            value = weight.toString(),
            onValueChanged = {},
            hint = "Total Weight (kg)",
            readOnly = true
        )
        /*
                AppTextField(
                    value = viewModel.remarks,
                    onValueChanged = {
                        viewModel.remarks = it
                    },
                    hint = "Remarks",
                    readOnly = false
                )*/


        val items = listOf<@Composable RowScope.() -> Unit> {
            TextInCircle(
                text = viewModel.selectedDate.ifEmpty { "Pickup Date" },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = LowPadding)
                    .noRippleClickable {
                        showDatePickerDialog = true
                    }, fontSize = fontSize
            )

            HorizontalSpace(space = LowSpacing)

            TextInCircle(
                text = viewModel.selectedTimeSlot.ifEmpty { "Pickup Time" },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = LowPadding)
                    .noRippleClickable {
                        showTimeSlotDialog = true
                    }, fontSize = fontSize
            )
        }
        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        SpaceBetweenRow(items = items)

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        AppDivider(thickness = 2.dp)

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        AppCheckBox(
            checked = viewModel.checked,
            onCheckedChange = {
                viewModel.checked = !viewModel.checked
            },
            text = "I agree with Terms conditions and Privacy Policy"
        )

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    T2PCustomerAppTheme {
        //BookingScreen()
    }
}