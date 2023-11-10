package me.taste2plate.app.customer.presentation.screens.citybrand

import android.text.Html
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun DetailScreen(
    viewModel: CityBrandViewModel,
) {

    AppScaffold(
        topBar = {
            AppTopBar(
                title = viewModel.selectedItem.name
            ) {}
        }
    ) {
        DetailsScreenContent(viewModel.selectedItem)
    }
}

@Composable
fun DetailsScreenContent(
    item: CommonForItem
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        NetworkImage(
            image = item.image, modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        VerticalSpace(space = SpaceBetweenViews)

        Text(
            text = Html.fromHtml(item.description ?: "", 1).toString(),
            modifier = Modifier
                .padding(horizontal = ScreenPadding)
        )
    }
}