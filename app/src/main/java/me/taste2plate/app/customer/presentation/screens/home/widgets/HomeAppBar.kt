package me.taste2plate.app.customer.presentation.screens.home.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.T2PApp
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.appName
import me.taste2plate.app.customer.presentation.utils.appNameWithFullForm
import me.taste2plate.app.customer.presentation.utils.tagLine
import me.taste2plate.app.customer.presentation.widgets.DrawableIconButton
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.MaterialIconButton
import me.taste2plate.app.customer.presentation.widgets.WishlistIconWithCount


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    onNavigationIconClick: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToWalletScreen: () -> Unit,
    onNavigateToCartScreen: () -> Unit,
    onNavigateToWishlistScreen: () -> Unit,
) {
    val buttonSize = 17.dp
    TopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.offset(x = ((-10).dp))
            ) {
                DrawableImage(
                    id = R.drawable.logo_new,
                    modifier = Modifier.size(30.dp)
                )
                HorizontalSpace(space = SpaceBetweenViewsAndSubViews)
                Column {
                    Text(appNameWithFullForm, fontSize = 16.sp)
                    Text(tagLine, fontSize = 12.sp)
                }
            }
        },
        navigationIcon = {
            MaterialIconButton(
                imageVector = Icons.Default.Menu
            ) {
                onNavigationIconClick()
            }
        },
        actions = {
            val modifier = Modifier.size(buttonSize)

            MaterialIconButton(
                modifier = modifier,
                imageVector = Icons.Outlined.Search,
            ) {
                onNavigateToSearchScreen()
            }
/*
            DrawableIconButton(
                modifier = modifier,
                painterResource = R.drawable.wallet,
            ) {
                onNavigateToWalletScreen()
            }*/

            MaterialIconButton(
                modifier = modifier,
                badge = true,
                badgeText = T2PApp.wishlistCount,
                imageVector = Icons.Outlined.FavoriteBorder
            ) {
                onNavigateToWishlistScreen()
            }

            MaterialIconButton(
                modifier = modifier,
                badge = true,
                badgeText = T2PApp.cartCount,
                imageVector = Icons.Outlined.ShoppingCart
            ) {
                onNavigateToCartScreen()
            }
        }
    )
}

@Preview
@Composable
fun HomeAppBarPreview() {
    T2PCustomerAppTheme {
        //HomeAppBar(){}
    }
}