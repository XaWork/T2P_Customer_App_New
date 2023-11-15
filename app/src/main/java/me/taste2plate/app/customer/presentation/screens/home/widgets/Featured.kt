package me.taste2plate.app.customer.presentation.screens.home.widgets

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.presentation.screens.home.FoodItemUpdateInfo
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.addToCartString
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.BlackBorderCard
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.ImageWithWishlistButton
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.MaterialIconButton
import me.taste2plate.app.customer.presentation.widgets.RatingInfoRow
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace


@Composable
fun SingleFeaturedItem(
    product: HomeModel.Featured,
    onNavigateToProductDetailsScreen: () -> Unit,
    alreadyWishListed: Boolean = false,
    foodItemUpdateInfo: FoodItemUpdateInfo? = null,
    addToWishlist: () -> Unit,
    addToCart: () -> Unit
) {
    BlackBorderCard(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.clickable { onNavigateToProductDetailsScreen() }
    ) {
        Column {
            ImageWithWishlistButton(
                image = product.file[0].location,
                alreadyWishListed = alreadyWishListed,
                foodItemUpdateInfo = foodItemUpdateInfo
            ) {
                addToWishlist()
            }

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Column(
                modifier = Modifier.padding(ScreenPadding)
            ) {
                RatingInfoRow(
                    flatOff = if (product.sellingPrice != null && product.sellingPrice.isNotEmpty())
                        "Flat $rupeeSign${(product.sellingPrice.toInt() - product.price.toInt())} OFF"
                    else "",
                    rating = "",
                    weight = product.weight
                )

                VerticalSpace(space = SpaceBetweenViews)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(2f)) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 2,
                            minLines = 2
                        )

                        Text(
                            text = product.brand.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Light
                        )

                        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                        InfoWithIcon(
                            icon = true,
                            imageVector = Icons.Outlined.LocationOn,
                            info = product.city.name
                        )

                        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                        InfoWithIcon(
                            icon = false,
                            id = R.drawable.delivery_bike,
                            info = "Estimate Delivery",
                            colorFilter = ColorFilter.tint(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                    DealsPriceCard(
                        product.price,
                        showAddToCartButton = false
                    ) {}
                }

                VerticalSpace(space = SpaceBetweenViews)

                val pathEffect = PathEffect.dashPathEffect(floatArrayOf(70f, 20f), 1f)
                Canvas(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                ) {
                    drawLine(
                        color = Color.Red,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        pathEffect = pathEffect
                    )
                }

                VerticalSpace(space = SpaceBetweenViews)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = addToCartString,
                        color = MaterialTheme.colorScheme.primary
                    )

                    HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

                    /*if (foodItemUpdateInfo != null && !foodItemUpdateInfo.wishlistItem && foodItemUpdateInfo.isLoading)
                        ShowLoading()
                    else*/
                        MaterialIconButton(
                            imageVector = Icons.Outlined.ShoppingCart,
                            tint = MaterialTheme.colorScheme.primary
                        ) {
                            addToCart()
                        }
                }
            }
        }
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FeaturedPreview() {
    T2PCustomerAppTheme {
        // SingleFeaturedItem(Product(), {})
    }
}