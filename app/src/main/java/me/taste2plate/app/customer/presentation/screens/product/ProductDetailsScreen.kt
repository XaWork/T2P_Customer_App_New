package me.taste2plate.app.customer.presentation.screens.product

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.domain.model.product.ProductDetailsModel
import me.taste2plate.app.customer.presentation.screens.home.widgets.DotsIndicator
import me.taste2plate.app.customer.presentation.screens.product.list.ProductEvents
import me.taste2plate.app.customer.presentation.theme.ExtraLowElevation
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.onSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.secondaryColor
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppOutlineButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.CircleIconButton
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.TextInCircle
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.utils.fromHtml
import me.taste2plate.app.customer.utils.toDate

@Composable
fun ProductDetailsScreen(
    viewModel: ProductViewModel,
    productId: String? = null,
    onNavigateToCartScreen: () -> Unit
) {

    val state = viewModel.state

    LaunchedEffect(Unit) {
        if (productId != null)
            viewModel.selectedProductId = productId

        viewModel.onEvent(ProductEvents.GetProductDetails)
    }

    AppScaffold(
        topBar = {
            AppTopBar {}
        },
        bottomBar = {
            BottomBar(
                onNavigateToCartScreen = onNavigateToCartScreen
            )
        }
    ) {

        if (state.isLoading || state.productDetails == null)
            ShowLoading(isButton = false)
        else if (state.isError)
            AppEmptyView()
        else {
            val details = state.productDetails.result[0]
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                item {
                    ProductImages(details)

                    VerticalSpace(space = SpaceBetweenViews)

                    Column(
                        modifier = Modifier.padding(ScreenPadding)
                    ) {
                        ProductDetails(details, onPinCodeCheck = {})

                        VerticalSpace(space = SpaceBetweenViews)

                        SpaceBetweenRow(item1 = {
                            Text(
                                text = "Reviews",
                                fontWeight = FontWeight.W500,
                                fontSize = 18.sp
                            )
                        }) {
                            Text(text = "Add Reviews", color = primaryColor.invoke())
                        }
                    }
                }

                items(state.productDetails.review) { review ->
                    ProductReviews(review)
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    onNavigateToCartScreen: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = primaryColor.invoke())
            .padding(ScreenPadding)
    ) {
        SpaceBetweenRow(item1 = {
            InfoWithIcon(
                info = "Items in cart", icon = true,
                imageVector = Icons.Default.ShoppingCart,
                textColor = backgroundColor.invoke(),
                tint = backgroundColor.invoke(),
                fontSize = 14.sp
            )
        }) {

            InfoWithIcon(
                info = "Cart",
                id = R.drawable.arrow_right,
                iconInStart = false,
                textColor = backgroundColor.invoke(),
                colorFilter = ColorFilter.tint(color = backgroundColor.invoke()),
                modifier = Modifier.clickable {
                    onNavigateToCartScreen()
                },
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ProductReviews(
    review: ProductDetailsModel.Review
) {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        modifier = Modifier.padding(ScreenPadding),
        elevation = ExtraLowElevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ScreenPadding)
        ) {
            Text(
                text = review.createdDate.toDate(),
                color = secondaryColor.invoke(),
                modifier = Modifier.align(Alignment.End)
            )

            /*VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            AppRatingBar(
                rating = 3.5f,
                spaceBetween = 3.dp
            )*/

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(text = review.review)

        }
    }
}

@Composable
fun ProductDetails(
    details: ProductDetailsModel.Result,
    onPinCodeCheck: (pin: String) -> Unit
) {
    var pincode by remember {
        mutableStateOf("")
    }

    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = ExtraLowElevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ScreenPadding)
        ) {

            val items = listOf<@Composable RowScope.() -> Unit> {
                Text(
                    text = details.name,
                    maxLines = 5,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                )

                CartAddRemove(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    cartItemLength = 0,
                ) {}
            }

            SpaceBetweenRow(items = items)

            VerticalSpace(space = SpaceBetweenViews)

            Text(
                text = "Check Availability",
                fontWeight = FontWeight.W400,
            )

            VerticalSpace(space = SpaceBetweenViews)

            SpaceBetweenRow(item1 = {
                TextField(
                    value = pincode,
                    onValueChange = {
                        if (it.length <= 6)
                            pincode = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = SpaceBetweenViewsAndSubViews)
                        .weight(2f),
                    shape = TextFieldDefaults.outlinedShape,
                    placeholder = {
                        Text(text = "Pincode")
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = backgroundColor.invoke(),
                        unfocusedContainerColor = backgroundColor.invoke()
                    ),
                )
            }) {
                AppOutlineButton(
                    text = "Check",
                    modifier = Modifier.weight(1f)
                ) {
                    onPinCodeCheck(pincode)
                }
            }

            VerticalSpace(space = SpaceBetweenViews)

            Text(text = details.desc.fromHtml(), fontWeight = FontWeight.Light)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductImages(
    details: ProductDetailsModel.Result,
) {
    val images = details.file
    val isOnSale = !details.sellingPrice.isNullOrEmpty()
    val pagerState = rememberPagerState(pageCount = { images.size })
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        HorizontalPager(
            state = pagerState,
            pageSpacing = 10.dp
        ) {
            NetworkImage(
                image = images[it].location,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
        }

        if (isOnSale)
            SaleBanner(modifier = Modifier.align(Alignment.TopStart))

        CircleIconButton(
            isDrawableIcon = false,
            icon = Icons.Default.FavoriteBorder,
            modifier = Modifier
                .padding(LowPadding)
                .align(Alignment.TopEnd)
                .size(30.dp)
        ) {}

        Column(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            if (isOnSale)
                SaleBanner(
                    text = "$rupeeSign ${details.sellingPrice}",
                    textDecoration = TextDecoration.LineThrough,
                    backgroundColor = secondaryColor.invoke()
                )
            SaleBanner(text = "$rupeeSign ${details.price}")
        }


        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = images.size,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }
}

@Composable
fun SaleBanner(
    modifier: Modifier = Modifier,
    text: String = "Sale",
    textDecoration: TextDecoration? = null,
    backgroundColor: Color = primaryColor.invoke()
) {
    Text(
        text = text,
        textDecoration = textDecoration,
        modifier = modifier
            .clip(RectangleShape)
            .background(color = backgroundColor)
            .padding(
                MediumPadding
            ),
        color = onSecondaryColor.invoke()
    )
}

@Composable
fun CartAddRemove(
    modifier: Modifier = Modifier,
    textInCircleModifier: Modifier = Modifier,
    cartItemLength: Int,
    textInCircleFontSize: TextUnit = 22.sp,
    fontSize: TextUnit = 18.sp,
    onUpdateCart: (quantity: Int) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextInCircle(
            fontSize = textInCircleFontSize,
            modifier = textInCircleModifier.clickable {
                if (cartItemLength > 0)
                    onUpdateCart(cartItemLength - 1)
            },
            text = "-"
        )

        Text(
            text = cartItemLength.toString(),
            modifier = Modifier
                .padding(
                    horizontal = MediumPadding,
                ),
            textAlign = TextAlign.Center,
            fontSize = fontSize
        )

        TextInCircle(
            fontSize = textInCircleFontSize,
            modifier = textInCircleModifier.clickable {
                onUpdateCart(cartItemLength + 1)
            },
            text = "+"
        )
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductDetailsScreenPreview() {
    T2PCustomerAppTheme {
        // ProductDetailsScreen({})
    }
}