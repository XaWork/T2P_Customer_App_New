package me.taste2plate.app.customer.presentation.screens.address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard

@Composable
fun AddressBottomSheet() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
        ) {
            items(10) {
                SingleAddressItem()
            }
        }

        AppButton(
            text = "Add New Address",
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {

        }
    }
}

@Composable
fun SingleAddressItem() {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = LowElevation
    ) {
        Text(
            "Address here",
            modifier = Modifier.padding(ScreenPadding)
        )
    }
}