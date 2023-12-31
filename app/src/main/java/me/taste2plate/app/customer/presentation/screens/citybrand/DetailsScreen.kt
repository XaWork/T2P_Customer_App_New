package me.taste2plate.app.customer.presentation.screens.citybrand

import android.text.Html
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun DetailScreen(
    viewModel: CityBrandViewModel,
    navigateBack: () -> Unit,
) {


    LaunchedEffect(true) {
        viewModel.onEvent(
            CityBrandEvents.AddLog(
                LogRequest(
                    type = LogType.pageVisit,
                    event = "enter in city brand details screen",
                    page_name = "/cityBrandDetails"
                )
            ))
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = viewModel.selectedItem.name
            ) {
                navigateBack()
            }
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