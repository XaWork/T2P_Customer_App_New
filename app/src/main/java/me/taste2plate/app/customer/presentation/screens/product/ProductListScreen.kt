package me.taste2plate.app.customer.presentation.screens.product

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.presentation.screens.productList
import me.taste2plate.app.customer.presentation.theme.MediumElevation
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppOutlineButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun ProductListScreen(
    onNavigateToProductDetailsScreen: () -> Unit
) {
    AppScaffold(
        topBar = {
            AppTopBar {}
        }
    ) {
        ContentProductListScreen(
            onNavigateToProductDetailsScreen = onNavigateToProductDetailsScreen
        )
    }
}

@Composable
fun ContentProductListScreen(
    onNavigateToProductDetailsScreen: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
    ) {
        items(10) {
            SingleProductItem(onNavigateToProductDetailsScreen = onNavigateToProductDetailsScreen)
        }
    }
}

@Composable
fun SingleProductItem(
    onNavigateToProductDetailsScreen: () -> Unit
) {
    val product = productList[0]
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = MediumElevation,
        modifier = Modifier.clickable {
            onNavigateToProductDetailsScreen()
        }
    ) {
        Column(
            modifier = Modifier.padding(ScreenPadding)
        ) {
            NetworkImage(
                image = product.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(MediumRoundedCorners)),
                contentScale = ContentScale.Crop
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            SpaceBetweenRow(item1 = {
                Column {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    VerticalSpace(space = VeryLowSpacing)

                    Text(
                        text = product.brand,
                        fontWeight = FontWeight.Light
                    )

                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                    InfoWithIcon(
                        icon = true,
                        imageVector = Icons.Outlined.LocationOn,
                        info = product.address
                    )

                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                    val priceInfo = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                            append("$rupeeSign${product.price} ")
                        }
                        withStyle(SpanStyle(fontWeight = FontWeight.Light)) {
                            append("2 Pieces")
                        }
                    }

                    Text(
                        text = priceInfo
                    )
                }
            }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        start = ScreenPadding
                    )
                ) {
                    AppOutlineButton(text = "Add") {}

                    Text(
                        text = "only 7 left",
                        color = primaryColor.invoke()
                    )
                }
            }
        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductListScreenPreview() {
    T2PCustomerAppTheme {
        ProductListScreen({})
    }
}