package me.taste2plate.app.customer.presentation.screens.citybrand

import android.content.res.Configuration
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.screens.productList
import me.taste2plate.app.customer.presentation.theme.HighPadding
import me.taste2plate.app.customer.presentation.theme.HighRoundedCorners
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.forestGreen
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow

@Composable
fun CityBrandScreen(
    onNavigateToProductListScreen: () -> Unit
) {
    AppScaffold(
        topBar = {
            AppTopBar {}
        }
    ) {
        ContentCityBrandScreen(
            onNavigateToProductListScreen = onNavigateToProductListScreen
        )
    }
}

@Composable
fun ContentCityBrandScreen(
    onNavigateToProductListScreen: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViews),
    ) {
        items(10) {
            SingleCityBrand(
                onNavigateToProductListScreen = onNavigateToProductListScreen
            )
        }
    }
}

@Composable
fun SingleCityBrand(
    onNavigateToProductListScreen: () -> Unit
) {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = LowElevation,
        modifier = Modifier.clickable { onNavigateToProductListScreen() }
    ) {
        Column {
            Box {
                NetworkImage(
                    image = productList[0].image, modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = "Name",
                    modifier = Modifier
                        .offset(y = (15).dp)
                        .clip(RoundedCornerShape(HighRoundedCorners))
                        .align(Alignment.BottomCenter)
                        .background(color = primaryColor.invoke())
                        .padding(
                            horizontal = HighPadding,
                            vertical = LowPadding
                        ),
                    color = backgroundColor.invoke()
                )

            }

            SpaceBetweenRow(
                modifier = Modifier.padding(ScreenPadding),
                item1 = {
                    Text("View Products", color = primaryColor.invoke())
                }) {
                Text(
                    "View Details", color = primaryColor.invoke(),
                    textDecoration = TextDecoration.Underline
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
        CityBrandScreen({})
    }
}