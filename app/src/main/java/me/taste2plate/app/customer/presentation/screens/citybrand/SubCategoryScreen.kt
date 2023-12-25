package me.taste2plate.app.customer.presentation.screens.citybrand

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading

@Composable
fun SubCategoryScreen(
    viewModel: CityBrandViewModel,
    navigateBack:() -> Unit,
    onNavigateToProductListScreen: (itemInfo: CommonForItem) -> Unit,
) {
    val state = viewModel.state

    LaunchedEffect(!state.showSubCategories) {
        viewModel.onEvent(CityBrandEvents.GetSubCategory)
    }

    LaunchedEffect(true) {
        viewModel.onEvent(
            CityBrandEvents.AddLog(
                LogRequest(
                    type = LogType.pageVisit,
                    event = "enter in sub category screen",
                    page_name = "/subCategory"
                )
            ))
    }


    AppScaffold(
        topBar = {
            AppTopBar(
                title = viewModel.selectedItem.name
            ) {navigateBack()}
        }
    ) {
        if (state.isLoading)
            ShowLoading(isButton = false)
        else if (state.subCategoryItemList.isEmpty())
            AppEmptyView()
        else
            ContentSubCategory(
                state.subCategoryItemList,
                onNavigateToProductListScreen = {
                    val itemInfo =
                        it.copy(type = CityBrandScreens.Category.name, description = null)
                    onNavigateToProductListScreen(itemInfo)
                },
            )

    }
}

@Composable
fun ContentSubCategory(
    items: List<CommonForItem>,
    onNavigateToProductListScreen: (item: CommonForItem) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViews),
    ) {
        items(items) { item ->
            SingleSubCategory(
                item,
                onNavigateToProductListScreen = { onNavigateToProductListScreen(item) },
            )
        }
    }
}

@Composable
fun SingleSubCategory(
    item: CommonForItem,
    onNavigateToProductListScreen: () -> Unit,
) {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = LowElevation,
        modifier = Modifier.noRippleClickable { onNavigateToProductListScreen() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NetworkImage(
                image = item.image, modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = item.name,
                modifier = Modifier
                    .padding(
                        SpaceBetweenViewsAndSubViews
                    ),
                color = primaryColor.invoke()
            )

            Text(
                text = item.description!!,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(
                        SpaceBetweenViewsAndSubViews
                    ),
            )
        }
    }
}
