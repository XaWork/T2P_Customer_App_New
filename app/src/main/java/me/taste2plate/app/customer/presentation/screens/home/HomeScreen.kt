package me.taste2plate.app.customer.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.screens.home.widgets.AddressBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.AutoSlidingCarousel
import me.taste2plate.app.customer.presentation.screens.home.widgets.HomeAppBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.SearchBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.TopList
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
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
                modifier = Modifier.padding(ScreenPadding)
            ) {
                item {
                    TopList()
                    AutoSlidingCarousel()
                }
            }
        }

    }
}

@Preview
@Composable
fun HomePreview() {
    T2PCustomerAppTheme {
        HomeScreen()
    }
}