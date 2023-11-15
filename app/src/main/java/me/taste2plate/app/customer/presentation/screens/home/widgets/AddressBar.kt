package me.taste2plate.app.customer.presentation.screens.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.YellowBanner
import me.taste2plate.app.customer.presentation.theme.YellowBannerDark
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.VegNonVegFilter

@Composable
fun AddressBar(
    address: String,
    checked: Boolean = false,
    onCheckChange: () -> Unit = {},
    showAddressSheet: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (isSystemInDarkTheme()) YellowBannerDark else YellowBanner)
            .padding(
                horizontal = ScreenPadding,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier
            .clickable { showAddressSheet() }
            .fillMaxWidth(1f)
            .weight(1f)) {
            MaterialIcon(
                imageVector = Icons.Outlined.LocationOn,
            )

            HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                text = address,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        VegNonVegFilter(
            modifier = Modifier
                .fillMaxWidth(1f)
                .weight(1f),
            checked = checked,
            onCheckChange = onCheckChange
        )
    }
}

@Preview
@Composable
fun AddressBarPreview() {
    T2PCustomerAppTheme {
        //  AddressBar("JhunJhunu, Rajasthan", {})
    }
}