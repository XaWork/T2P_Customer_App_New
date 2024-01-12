package me.taste2plate.app.customer.presentation.screens.address

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.dialog.DeleteAddressDialog
import me.taste2plate.app.customer.presentation.screens.auth.AuthEvents
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.theme.whatsappColor
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.MaterialIconButton
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.showToast

@Composable
fun AddressListScreen(
    onNavigateToEditAddEditScreen: () -> Unit,
    onPopUpToAddEditScreen: () -> Unit,
    viewModel: AddressViewModel,
    navigateBack: () -> Unit,
) {
    val state = viewModel.state
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        viewModel.onEvent(
            AddressEvents.AddLog(
                LogRequest(
                    type = LogType.pageVisit,
                    event = "enter in address list screen",
                    page_name = "/address"
                )
            )
        )
    }

    if (showDeleteDialog)
        DeleteAddressDialog(onDismissRequest = {
            showDeleteDialog = false
        }) {
            showDeleteDialog = false
            viewModel.onEvent(AddressEvents.DeleteAddress)
        }

    LaunchedEffect(key1 = state) {
        when {
            state.isError || state.message != null -> {
                showToast(state.message!!)
                viewModel.onEvent(AddressEvents.GetAddressList)
            }

            state.deleteAddressResponse != null && state.addressList.isEmpty() -> {
                // onPopUpToAddEditScreen()
            }
        }
    }




    AppScaffold(
        topBar = {
            AppTopBar(
                title = "My Addresses"
            ) { navigateBack() }
        },
        floatingActionButton = {
            MaterialIconButton(
                imageVector = Icons.Default.AddBusiness,
                tint = screenBackgroundColor.invoke(),
                modifier = Modifier
                    .background(primaryColor.invoke())
                    .clip(CircleShape)
                    .padding(MediumPadding)
            ) {
                viewModel.onEvent(AddressEvents.StoreAddressId(-1))
                onNavigateToEditAddEditScreen()
            }
        }
    ) {
        ContentAddressListScreen(state,
            onEdit = {
                viewModel.onEvent(AddressEvents.StoreAddressId(it))
                onNavigateToEditAddEditScreen()
            },
            onDelete = {
                viewModel.onEvent(AddressEvents.StoreAddressId(it))
                showDeleteDialog = true
            },
            onNavigateToAddAddressScreen = {
                viewModel.onEvent(AddressEvents.StoreAddressId(-1))
                onNavigateToEditAddEditScreen()
            }
        )
    }
}

@Composable
fun ContentAddressListScreen(
    state: AddressState,
    onNavigateToAddAddressScreen: () -> Unit,
    onEdit: (addressId: Int) -> Unit,
    onDelete: (addressId: Int) -> Unit
) {
    val addressList = state.addressList

    if (state.isLoading)
        ShowLoading(isButton = false)
    else
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (addressList.isNotEmpty())
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(ScreenPadding),
                    contentPadding = PaddingValues(
                        top = ScreenPadding,
                        start = ScreenPadding,
                        bottom = 70.dp,
                        end = ScreenPadding
                    )
                ) {
                    itemsIndexed(addressList) { index, address ->

                        SingleAddressItem(address = address, defaultAddress = null,
                            showEditDelete = true,
                            onEdit = { onEdit(index) },
                            onDelete =
                            { onDelete(index) }
                        )
                        /*SingleAddressItem(
                            address,
                            onEdit = { onEdit(index) }) {
                            onDelete(index)
                        }*/
                    }
                }
            else
                Column {
                    AppEmptyView()
                }

           /* AppButton(
                text = "Add New Address",
                modifier = Modifier.align(
                    Alignment.BottomCenter
                )
            ) {
                onNavigateToAddAddressScreen()
            }*/
        }
}

@Composable
fun SingleAddressItem(
    address: AddressListModel.Result,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = LowElevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ScreenPadding)
        ) {
            Text(address.address)

            VerticalSpace(space = SpaceBetweenViews)

            SpaceBetweenRow(item1 = {
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .weight(1f)
                        .padding(end = VeryLowSpacing),
                    text = "Delete"
                ) {
                    onDelete()
                }
            }) {
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .weight(1f)
                        .padding(start = VeryLowSpacing),
                    text = "Edit",
                    buttonColors = ButtonDefaults.buttonColors(containerColor = whatsappColor)
                ) {
                    onEdit()
                }
            }
        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddressListScreenPreview() {
    T2PCustomerAppTheme {
        //AddressListScreen {}
    }
}