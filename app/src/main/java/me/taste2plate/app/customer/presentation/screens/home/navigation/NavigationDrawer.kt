package me.taste2plate.app.customer.presentation.screens.home.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.home.HomeScreen
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    onItemClick : (String) -> Unit,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RoundedCornerShape(
                    topEnd = MediumRoundedCorners,
                    bottomEnd = MediumRoundedCorners
                )
            ) {
                LazyColumn {
                    item {
                        DrawableImage(
                            id = R.drawable.header_bg,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentScale = ContentScale.Crop
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.primary)
                                .padding(LowPadding),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DrawableImage(
                                id = R.drawable.logo_new,
                                modifier = Modifier.size(40.dp)
                            )

                            HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

                            Text(
                                text = "Hey user",
                                color = MaterialTheme.colorScheme.background
                            )
                        }

                        VerticalSpace(space = SpaceBetweenViewsAndSubViews)
                    }

                    items(drawerItems) { item ->
                        NavigationDrawerItem(
                            label = {
                                InfoWithIcon(
                                    id = item.icon,
                                    info = item.title,
                                    colorFilter = ColorFilter.tint(
                                        color = if (item.selected) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.onBackground
                                    ),
                                    iconOrImageModifier = Modifier.size(24.dp),
                                    textColor = if (item.selected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onBackground
                                )
                            },
                            selected = item.selected,
                            onClick = {
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                                onItemClick(item.id)
                            })
                    }
                }
            }
        }) {
        content()
    }
}

fun getScreenBasedOnIndex(index: Int) = when (index) {
    0 -> DrawerAppScreen.Home
    1 -> DrawerAppScreen.Orders
    2 -> DrawerAppScreen.Profile
    3 -> DrawerAppScreen.BulkOrders
    4 -> DrawerAppScreen.MembershipPlan
    5 -> DrawerAppScreen.MyPlan
    6 -> DrawerAppScreen.RateApp
    7 -> DrawerAppScreen.ShareApp
    8 -> DrawerAppScreen.ContactUs
    9 -> DrawerAppScreen.LogOut
    else -> DrawerAppScreen.Home
}