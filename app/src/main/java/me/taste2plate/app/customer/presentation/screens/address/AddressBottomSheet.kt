package me.taste2plate.app.customer.presentation.screens.address

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.screens.checkout.fontSize
import me.taste2plate.app.customer.presentation.screens.home.CenterColumn
import me.taste2plate.app.customer.presentation.theme.ExtraHighPadding
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.theme.whatsappColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.MaterialIconButton
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow

@Composable
fun AddressBottomSheet(
    isLoading: Boolean = false,
    addressList: List<AddressListModel.Result>,
    defaultAddress: AddressListModel.Result?,
    onNavigateToAddressListScreen: () -> Unit,
    setDefaultAddress: (address: AddressListModel.Result) -> Unit
) {
    AppScaffold(
        modifier = Modifier
            .fillMaxWidth()
            .background(screenBackgroundColor.invoke()),
        floatingActionButton = {
            MaterialIconButton(
                imageVector = Icons.Default.AddBusiness,
                tint = screenBackgroundColor.invoke(),
                modifier = Modifier
                    .background(primaryColor.invoke())
                    .clip(CircleShape)
                    .padding(MediumPadding)
            ) {
                onNavigateToAddressListScreen()
            }
        }
    ) {
        if (isLoading)
            ShowLoading(isButton = true)
        else {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = ScreenPadding,
                    end = ScreenPadding,
                    top = ScreenPadding,
                    bottom = ExtraHighPadding + ExtraHighPadding
                ),
                verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews),
                modifier = Modifier.fillMaxSize()
            ) {
                if (addressList.isNotEmpty())
                    items(addressList) { address ->
                        SingleAddressItem(
                            modifier = Modifier.noRippleClickable {
                                setDefaultAddress(address)
                            },
                            defaultAddress = defaultAddress,
                            address = address
                        )
                    }
                else
                    item {

                        AppEmptyView(text = "No Address Found. Please add one.")
                       /* Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("No Address Found. Please add one.")
                        }*/
                    }
            }
        }
    }
}

@Composable
fun SingleAddressItem(
    modifier: Modifier = Modifier,
    address: AddressListModel.Result,
    showEditDelete: Boolean = false,
    defaultAddress: AddressListModel.Result?,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    RoundedCornerCard(
        modifier = modifier,
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = LowElevation
    ) {
        Column {
            if (defaultAddress?.id == address.id)
                Text(
                    "Default delivery location",
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = MediumRoundedCorners,
                                bottomEnd = MediumRoundedCorners
                            )
                        )
                        .background(primaryColor.invoke())
                        .padding(vertical = LowPadding, horizontal = MediumPadding),
                    color = screenBackgroundColor.invoke()
                )


            val userInfo = listOf<@Composable RowScope.() -> Unit> {
                Text(
                    address.contactName,
                    fontWeight = FontWeight.W400,
                    fontSize = 18.sp
                )

                Text(
                    text = address.title,
                    fontWeight = FontWeight.Light,
                    fontSize = fontSize,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(LowRoundedCorners)
                        )
                        .padding(5.dp)
                )
            }

            SpaceBetweenRow(
                items = userInfo,
                modifier = Modifier.padding(horizontal = ScreenPadding, vertical = LowPadding)
            )

            Text(
                "${address.address}\n${address.address2}\n${address.city.name}\n${address.state.name}",
                modifier = Modifier.padding(horizontal = ScreenPadding)
            )

            val contactInfo = listOf<@Composable RowScope.() -> Unit> {
                InfoWithIcon(
                    icon = true,
                    info = address.pincode
                )
                InfoWithIcon(
                    icon = true,
                    imageVector = Icons.Default.Phone,
                    info = "+91 ${address.contactMobile}"
                )
            }

            SpaceBetweenRow(
                items = contactInfo,
                modifier = Modifier.padding(horizontal = ScreenPadding, vertical = ScreenPadding)
            )

            if (showEditDelete)
                SpaceBetweenRow(
                    modifier = Modifier.padding(ScreenPadding),
                    item1 = {
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
@Composable
fun SingleAddressItemComposable() {
}