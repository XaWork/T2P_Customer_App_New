package me.taste2plate.app.customer.presentation.screens.address

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.theme.ExtraHighPadding
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading

@Composable
fun AddressBottomSheet(
    isLoading: Boolean = false,
    addressList: List<AddressListModel.Result>,
    onNavigateToAddressListScreen: () -> Unit,
    setDefaultAddress: (address: AddressListModel.Result) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isLoading || addressList.isEmpty())
            ShowLoading(isButton = false)
        else {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = ScreenPadding,
                    end = ScreenPadding,
                    top = ScreenPadding,
                    bottom = ExtraHighPadding + ExtraHighPadding
                ),
                verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
            ) {
                items(addressList) { address ->
                    SingleAddressItem(
                        modifier = Modifier.noRippleClickable {
                            setDefaultAddress(address)
                        },
                        address
                    )
                }
            }

            AppButton(
                text = "Add New Address",
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                onNavigateToAddressListScreen()
            }
        }

    }
}

@Composable
fun SingleAddressItem(
    modifier: Modifier = Modifier,
    address: AddressListModel.Result
) {
    RoundedCornerCard(
        modifier = modifier,
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = LowElevation
    ) {
        Text(
            address.address,
            modifier = Modifier.padding(ScreenPadding)
        )
    }
}