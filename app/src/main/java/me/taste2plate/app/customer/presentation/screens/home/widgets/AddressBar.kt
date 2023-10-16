package me.taste2plate.app.customer.presentation.screens.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.YellowBanner
import me.taste2plate.app.customer.presentation.theme.YellowBannerDark
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon

@Composable
fun AddressBar(
    address: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if(isSystemInDarkTheme()) YellowBannerDark else YellowBanner)
            .padding(
                horizontal = ScreenPadding,
                vertical = 2.dp
            )
            .clickable { onClick() }
    ) {
        MaterialIcon(
            imageVector = Icons.Outlined.LocationOn,
        )
        HorizontalSpace(space = SpaceBetweenViewsAndSubViews)
        Text(text = address)
    }
}

@Preview
@Composable
fun AddressBarPreview() {
    T2PCustomerAppTheme {
        AddressBar("JhunJhunu, Rajasthan", {})
    }
}