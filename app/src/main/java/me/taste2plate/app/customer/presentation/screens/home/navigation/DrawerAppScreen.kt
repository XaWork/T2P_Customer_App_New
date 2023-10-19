package me.taste2plate.app.customer.presentation.screens.home.navigation

import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.utils.bulkOrderString
import me.taste2plate.app.customer.presentation.utils.contactUsString
import me.taste2plate.app.customer.presentation.utils.homeString
import me.taste2plate.app.customer.presentation.utils.logOutString
import me.taste2plate.app.customer.presentation.utils.membershipPlansString
import me.taste2plate.app.customer.presentation.utils.myPlanString
import me.taste2plate.app.customer.presentation.utils.ordersString
import me.taste2plate.app.customer.presentation.utils.profileString
import me.taste2plate.app.customer.presentation.utils.rateAppString
import me.taste2plate.app.customer.presentation.utils.referAppAndEarnPointsString
import me.taste2plate.app.customer.presentation.utils.walletString

enum class DrawerAppScreen {
    Home,
    Orders,
    Profile,
    BulkOrders,
    MembershipPlan,
    MyPlan,
    RateApp,
    ShareApp,
    ContactUs,
    LogOut,
}

data class DrawerItem(
    val icon: Int,
    val title: String,
    val selected: Boolean = false
)

val drawerItems = listOf(
    DrawerItem(
        icon = R.drawable.ic_store_mall_directory_green_24dp,
        title = homeString,
        selected = true
    ),

    DrawerItem(
        icon = R.drawable.ic_local_dining_green_24dp,
        title = ordersString,
    ),

    DrawerItem(
        icon = R.drawable.ic_person_pin_green_24dp,
        title = profileString,
    ),

    DrawerItem(
        icon = R.drawable.shopping_bag,
        title = bulkOrderString,
    ),

    DrawerItem(
        icon = R.drawable.ic_rank,
        title = membershipPlansString,
    ),

    DrawerItem(
        icon = R.drawable.food_plan,
        title = myPlanString,
    ),

    DrawerItem(
        icon = R.drawable.wallet,
        title = walletString,
    ),

    DrawerItem(
        icon = R.drawable.ic_thumb_up_green_24dp,
        title = rateAppString,
    ),

    DrawerItem(
        icon = R.drawable.ic_share_green_24dp,
        title = referAppAndEarnPointsString,
    ),

    DrawerItem(
        icon = R.drawable.ic_person_pin_green_24dp,
        title = contactUsString,
    ),

    DrawerItem(
        icon = R.drawable.ic_logout,
        title = logOutString,
    ),

    )