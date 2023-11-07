package me.taste2plate.app.customer.presentation.screens.product

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.productList
import me.taste2plate.app.customer.presentation.theme.ExtraLowElevation
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
import me.taste2plate.app.customer.presentation.widgets.AppOutlineButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.CircleIconButton
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.TextInCircle
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun ProductDetailsScreen(
    onNavigateToCartScreen: () -> Unit
) {

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(top = ScreenPadding)
        ) {
            item {
                ProductImages()

                VerticalSpace(space = SpaceBetweenViews)

                Column(
                    modifier = Modifier.padding(ScreenPadding)
                ) {
                    ProductDetails()

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

            items(10) {
                ProductReviews()
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
                tint = backgroundColor.invoke()
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
                }
            )
        }
    }
}

@Composable
fun ProductReviews() {
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
                text = "07-05-2023",
                color = secondaryColor.invoke(),
                modifier = Modifier.align(Alignment.End)
            )

            /*VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            AppRatingBar(
                rating = 3.5f,
                spaceBetween = 3.dp
            )*/

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(text = "Review")

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetails() {
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
            SpaceBetweenRow(item1 = {
                Text(
                    text = "Product name",
                    maxLines = 5,
                    fontWeight = FontWeight.W500,
                    fontSize = 22.sp,
                )
            }) {
               // CartAddRemove()
            }

            VerticalSpace(space = SpaceBetweenViews)

            Text(
                text = "Check Availability",
                fontWeight = FontWeight.W500,
            )

            VerticalSpace(space = SpaceBetweenViews)

            SpaceBetweenRow(item1 = {
                TextField(
                    value = pincode,
                    onValueChange = {
                        pincode = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = SpaceBetweenViewsAndSubViews)
                        .weight(2f),
                    shape = TextFieldDefaults.outlinedShape,
                    placeholder = {
                        Text(text = "Pincode")
                    },
                    colors = TextFieldDefaults.textFieldColors(containerColor = backgroundColor.invoke()),
                )
            }) {
                AppOutlineButton(
                    text = "Check",
                    modifier = Modifier.weight(1f)
                ) {}
            }

            VerticalSpace(space = SpaceBetweenViews)

            Text(text = "Description", fontWeight = FontWeight.Light)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductImages() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        val pagerState = rememberPagerState(pageCount = { 5 })

        HorizontalPager(
            state = pagerState,
            pageSpacing = 10.dp
        ) {
            NetworkImage(
                image = productList[0].image,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(
                        RoundedCornerShape(MediumRoundedCorners)
                    ),
                contentScale = ContentScale.Crop
            )
        }

        SaleBanner(modifier = Modifier.align(Alignment.TopStart))

        CircleIconButton(
            painterResource = R.drawable.heart,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
        ) {}

        Column(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            SaleBanner(
                text = "$rupeeSign 324", textDecoration = TextDecoration.LineThrough,
                backgroundColor = secondaryColor.invoke()
            )
            SaleBanner(text = "$rupeeSign 224")
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
    cartItemLength: Int,
    onUpdateCart: (quantity: Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextInCircle(
            modifier = Modifier.clickable {
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
            fontSize = 18.sp
        )

        TextInCircle(
            modifier = Modifier.clickable {
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
        ProductDetailsScreen({})
    }
}