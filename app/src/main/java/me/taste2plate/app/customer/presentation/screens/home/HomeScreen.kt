package me.taste2plate.app.customer.presentation.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.screens.ImageItemList
import me.taste2plate.app.customer.presentation.screens.home.widgets.AddressBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.AutoSlidingCarousel
import me.taste2plate.app.customer.presentation.screens.home.widgets.HeadingChip
import me.taste2plate.app.customer.presentation.screens.home.widgets.HeadingChipWithLine
import me.taste2plate.app.customer.presentation.screens.home.widgets.HomeAppBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.MostOrderedItemList
import me.taste2plate.app.customer.presentation.screens.home.widgets.SearchBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.SingleTopList
import me.taste2plate.app.customer.presentation.screens.home.widgets.TopList
import me.taste2plate.app.customer.presentation.screens.home.widgets.TopOrderedFoodCityList
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun HomeScreen() {
    var searchValue by remember {
        mutableStateOf("")
    }
    AppScaffold(
        topBar = {
            HomeAppBar()
        }
    ) {
        Column {
            SearchBar(value = searchValue, onValueChange = {
                searchValue = it
            }) {}
            AddressBar("Address here") {}
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    TopList()
                    AutoSlidingCarousel()
                    VerticalSpace(space = SpaceBetweenViews)
                    HeadingChip("Top Ordered Food/City")
                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)
                    TopOrderedFoodCityList()
                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)
                    MostOrderedItemList()
                    VerticalSpace(space = SpaceBetweenViews)
                }
            }
        }

    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    T2PCustomerAppTheme {
        HomeScreen()
    }
}