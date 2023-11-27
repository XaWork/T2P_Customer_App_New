package me.taste2plate.app.customer.presentation.screens.citybrand

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens
import me.taste2plate.app.customer.presentation.screens.home.widgets.AppSearchBar
import me.taste2plate.app.customer.presentation.theme.HighPadding
import me.taste2plate.app.customer.presentation.theme.HighRoundedCorners
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun CityBrandScreen(
    screen: CityBrandScreens,
    viewModel: CityBrandViewModel,
    onNavigateToProductListScreen: (itemInfo: CommonForItem) -> Unit,
    onNavigateToDetailsScreen: () -> Unit,
    onNavigateToSubCategoryScreen: () -> Unit,
    navigateBack: () -> Unit,
) {
    val state = viewModel.state

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(CityBrandEvents.GetData(screen))
    }


    AppScaffold(
        topBar = {
            AppTopBar(
                title = when (screen) {
                    CityBrandScreens.City -> "City"
                    CityBrandScreens.Brand -> "Brand"
                    CityBrandScreens.Category -> "Explore"
                    else -> "Flavours Of India"
                }
            ) { navigateBack() }
        }
    ) {
        if (state.isLoading)
            ShowLoading(isButton = false)
        else if (state.itemList.isEmpty())
            AppEmptyView()
        else
            ContentCityBrandScreen(
                state.itemList,
                onNavigateToProductListScreen = {
                    if (screen == CityBrandScreens.Category) {
                        viewModel.onEvent(CityBrandEvents.SetSelectedItem(it))
                        onNavigateToSubCategoryScreen()
                    } else {
                        val itemInfo = it.copy(type = screen.name, description = null)
                        onNavigateToProductListScreen(itemInfo)
                    }
                },
                onNavigateToDetailsScreen = {
                    viewModel.onEvent(CityBrandEvents.SetSelectedItem(it))
                    onNavigateToDetailsScreen()
                }
            )
    }
}

@Composable
fun ContentCityBrandScreen(
    items: List<CommonForItem>,
    onNavigateToProductListScreen: (item: CommonForItem) -> Unit,
    onNavigateToDetailsScreen: (item: CommonForItem) -> Unit,
) {
    var searchValue by remember {
        mutableStateOf("")
    }

    Column {
        AppSearchBar(
            value = searchValue,
            onValueChange = {
                searchValue = it
            }
        )

        var modifyList = mutableListOf<CommonForItem>()
        if (searchValue.isEmpty()) modifyList = items.toMutableList() else {
            for (item in items) {
                if (item.name.contains(searchValue, ignoreCase = true)) {
                    modifyList.add(item)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(SpaceBetweenViews),
        ) {

            items(modifyList) { item ->
                SingleCityBrand(
                    item,
                    onNavigateToProductListScreen = { onNavigateToProductListScreen(item) },
                    onNavigateToDetailsScreen = { onNavigateToDetailsScreen(item) }
                )
            }
        }
    }
}

@Composable
fun SingleCityBrand(
    item: CommonForItem,
    onNavigateToProductListScreen: () -> Unit,
    onNavigateToDetailsScreen: () -> Unit,
) {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = LowElevation,
        modifier = Modifier.noRippleClickable { onNavigateToProductListScreen() }
    ) {
        Column {
            Box {
                NetworkImage(
                    image = item.image, modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = item.name,
                    modifier = Modifier
                        .offset(y = (15).dp)
                        .clip(RoundedCornerShape(HighRoundedCorners))
                        .align(Alignment.BottomCenter)
                        .background(color = primaryColor.invoke())
                        .padding(
                            horizontal = HighPadding,
                            vertical = LowPadding
                        ),
                    color = backgroundColor.invoke(),
                    fontSize = 14.sp
                )

            }

            SpaceBetweenRow(
                modifier = Modifier.padding(ScreenPadding),
                item1 = {
                    Text("View Products", color = primaryColor.invoke(), modifier =
                    Modifier.noRippleClickable { onNavigateToProductListScreen() })
                }) {
                Text(
                    "View Details", color = primaryColor.invoke(),
                    textDecoration = TextDecoration.Underline, modifier =
                    Modifier.noRippleClickable { onNavigateToDetailsScreen() }
                )
            }
        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CityBrandScreenPreview() {
    T2PCustomerAppTheme {
        // CityBrandScreen({})
    }
}