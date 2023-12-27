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
    Wallet,
    About,
    Refund,
    Terms,
    Privacy,
    MembershipPlan,
    MyPlan,
    RateApp,
    ReferAndEarn,
    ShareApp,
    ContactUs,
    LogOut,
}

data class DrawerItem(
    val id: String,
    val icon: Int,
    val title: String,
    val selected: Boolean = false
)

val drawerItems = listOf(
    DrawerItem(
        id = DrawerAppScreen.Home.name,
        icon = R.drawable.ic_store_mall_directory_green_24dp,
        title = homeString,
        selected = true
    ),

    DrawerItem(
        id = DrawerAppScreen.Orders.name,
        icon = R.drawable.ic_local_dining_green_24dp,
        title = ordersString,
    ),

    DrawerItem(
        id = DrawerAppScreen.Profile.name,
        icon = R.drawable.ic_person_pin_green_24dp,
        title = profileString,
    ),

    DrawerItem(
        id = DrawerAppScreen.BulkOrders.name,
        icon = R.drawable.shopping_bag,
        title = bulkOrderString,
    ),

    DrawerItem(
        id = DrawerAppScreen.MembershipPlan.name,
        icon = R.drawable.ic_rank,
        title = membershipPlansString,
    ),

    DrawerItem(
        id = DrawerAppScreen.MyPlan.name,
        icon = R.drawable.food_plan,
        title = myPlanString,
    ),

    DrawerItem(
        id = DrawerAppScreen.Wallet.name,
        icon = R.drawable.wallet,
        title = walletString,
    ),

    DrawerItem(
        id = DrawerAppScreen.About.name,
        icon = R.drawable.about,
        title = "About Us",
    ),

    DrawerItem(
        id = DrawerAppScreen.Refund.name,
        icon = R.drawable.refund,
        title = "Refund Policy",
    ),

    DrawerItem(
        id = DrawerAppScreen.Terms.name,
        icon = R.drawable.terms,
        title = "Terms & Conditions",
    ),

    DrawerItem(
        id = DrawerAppScreen.Privacy.name,
        icon = R.drawable.privacy_policy,
        title = "Privacy & Policies",
    ),

    DrawerItem(
        id = DrawerAppScreen.RateApp.name,
        icon = R.drawable.ic_thumb_up_green_24dp,
        title = rateAppString,
    ),

    DrawerItem(
        id = DrawerAppScreen.ReferAndEarn.name,
        icon = R.drawable.ic_share_green_24dp,
        title = referAppAndEarnPointsString,
    ),

    DrawerItem(
        id = DrawerAppScreen.ContactUs.name,
        icon = R.drawable.ic_person_pin_green_24dp,
        title = contactUsString,
    ),

    DrawerItem(
        id = DrawerAppScreen.LogOut.name,
        icon = R.drawable.ic_logout,
        title = logOutString,
    ),

    )