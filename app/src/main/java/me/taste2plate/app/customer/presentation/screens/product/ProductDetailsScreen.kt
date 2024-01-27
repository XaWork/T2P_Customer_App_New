package me.taste2plate.app.customer.presentation.screens.product

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.model.product.ProductDetailsModel
import me.taste2plate.app.customer.presentation.dialog.SettingDialogType
import me.taste2plate.app.customer.presentation.dialog.SettingInfoDialog
import me.taste2plate.app.customer.presentation.screens.home.widgets.DotsIndicator
import me.taste2plate.app.customer.presentation.screens.product.list.ProductEvents
import me.taste2plate.app.customer.presentation.screens.product.list.ProductListState
import me.taste2plate.app.customer.presentation.theme.ExtraLowElevation
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.forestGreen
import me.taste2plate.app.customer.presentation.theme.onSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.theme.secondaryColor
import me.taste2plate.app.customer.presentation.theme.whatsappColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.CircleIconButton
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.showToast
import me.taste2plate.app.customer.utils.fromHtml
import me.taste2plate.app.customer.utils.toDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    viewModel: ProductViewModel,
    productId: String? = null,
    onNavigateToCartScreen: () -> Unit,
    navigateBack: () -> Unit,
) {

    val state = viewModel.state
    val context = LocalContext.current

    var showSettingDialog by remember {
        mutableStateOf(false)
    }
    if (showSettingDialog) {
        SettingInfoDialog(
            setting = state.settings!!,
            type = SettingDialogType.Cart,
            onDismissRequest = {
                showSettingDialog = false
                viewModel.onEvent(ProductEvents.UpdateState)
            }) {
            showSettingDialog = false
            viewModel.onEvent(ProductEvents.UpdateState)
        }
    }

    LaunchedEffect(Unit) {
        if (!productId.isNullOrEmpty())
            viewModel.selectedProductId = productId

        viewModel.onEvent(
            ProductEvents.AddLog(
                LogRequest(
                    type = LogType.pageVisit,
                    event = "enter in product details screen",
                    page_name = "/home",
                    product_id = viewModel.selectedProductId
                )
            )
        )

        viewModel.onEvent(ProductEvents.GetProductDetails)
    }

    LaunchedEffect(key1 = state) {
        if (state.message != null && !state.cartError) {
            showToast(state.message, toastLength = Toast.LENGTH_LONG)
            viewModel.onEvent(ProductEvents.UpdateState)
        }
        if (state.cartError)
            showSettingDialog = true
    }

    var shoReviewBottomSheet by remember {
        mutableStateOf(false)
    }
    if (shoReviewBottomSheet) {
        ModalBottomSheet(
            containerColor = screenBackgroundColor.invoke(),
            onDismissRequest = {
                shoReviewBottomSheet = false
            },
            sheetState = rememberModalBottomSheetState()
        ) {
            AddReviewBottomSheet { rating, review ->
                shoReviewBottomSheet = false
                viewModel.onEvent(ProductEvents.PostReview(rating, review))
            }
        }
    }

    AppScaffold(
        topBar = {
            AppTopBar { navigateBack() }
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                item {
                    ProductImages(state) {
                        viewModel.onEvent(ProductEvents.AddToWishlist(productId = productId!!))
                    }

                    VerticalSpace(space = SpaceBetweenViews)

                    Column(
                        modifier = Modifier.padding(ScreenPadding)
                    ) {
                        ProductDetails(
                            state = state,
                            updateCart = {
                                viewModel.onEvent(
                                    ProductEvents.UpdateCart(
                                        quantity = it,
                                        productId = state.productDetails.result[0].id,
                                        context = context
                                    )
                                )
                            },
                            onPinCodeCheck = {
                                viewModel.onEvent(ProductEvents.CheckAvailibility(it))
                            })

                        VerticalSpace(space = SpaceBetweenViews)

                        SpaceBetweenRow(item1 = {
                            Text(
                                text = "Reviews",
                                fontWeight = FontWeight.W500,
                                fontSize = 18.sp
                            )
                        }) {
                            if (state.reviewLoading)
                                ShowLoading()
                            else
                                Text(text = "Add Reviews", color = primaryColor.invoke(),
                                    modifier = Modifier.noRippleClickable {
                                        shoReviewBottomSheet = true
                                    })
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
                modifier = Modifier.noRippleClickable {
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
    state: ProductListState,
    onPinCodeCheck: (pin: String) -> Unit,
    updateCart: (quantity: Int) -> Unit
) {
    val cartData = state.cartData
    val details = state.productDetails!!.result[0]

    var pincode by remember {
        mutableStateOf(state.defaultAddress?.pincode ?: state.localAddress?.pinCode ?: "")
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
            Text("Total Weight : ${details.weight ?: ""} kg", color = secondaryColor.invoke())

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${details.name} ",
                    maxLines = 5,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                )

                //get cart item length
                var cartItemLength = 0
                if (cartData != null && cartData.result != null && cartData.result.isNotEmpty()) {
                    cartData.result.forEach {
                        if (it.product.id == details.id)
                            cartItemLength = it.quantity
                    }
                }
                if (state.addToCartEnable)
                    CartAddRemove(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        cartItemLength = cartItemLength,
                    ) {
                        updateCart(it)
                    }
                else
                    Text("")
                //}
            }

            /*val items = listOf<@Composable RowScope.() -> Unit> {
                Text(
                    text = details.name ?: "",
                    maxLines = 5,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                )


                //get cart item length
                var cartItemLength = 0
                cartData!!.result.forEach {
                    if (it.product.id == details.id)
                        cartItemLength = it.quantity
                }
                if (state.addToCartEnable)
                    CartAddRemove(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        cartItemLength = cartItemLength,
                    ) {
                        updateCart(it)
                    }
                else
                    Text("")
            }

            SpaceBetweenRow(items = items)*/

            VerticalSpace(space = SpaceBetweenViews)

            Text(
                text = "Last Mile Delivery - Your food will travel ${state.productDetails.distance}Kms in your city to reach to you.",
                fontWeight = FontWeight.W400,
                color = primaryColor.invoke()
            )

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
                if (state.buttonLoading)
                    ShowLoading()
                else
                    AppButton(
                        text = "Check",
                        modifier = Modifier.weight(1f),
                        buttonColors = ButtonDefaults.buttonColors(
                            contentColor = screenBackgroundColor.invoke(),
                            containerColor = whatsappColor
                        ),
                    ) {
                        onPinCodeCheck(pincode)
                    }
            }

            VerticalSpace(space = VeryLowSpacing)

            if (state.checkAvailabilityModel != null && state.checkAvailabilityModel.status == Status.success.name) {
                // if (state.checkAvailabilityModel.express)
                Column {
                    // if (state.checkAvailabilityModel.express)
                    InfoWithIcon(
                        id = R.drawable.delivery_bike,
                        info = state.checkAvailabilityModel.cutoff_response.express_remarks,
                        modifier = Modifier.padding(vertical = VeryLowSpacing),
                        iconOrImageModifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(color = forestGreen.invoke()),
                        maxLines = 10,
                        textColor = forestGreen.invoke()
                    )

                    InfoWithIcon(
                        id = R.drawable.delivery_bike,
                        info = state.checkAvailabilityModel.cutoff_response.remarks,
                        modifier = Modifier.padding(vertical = VeryLowSpacing),
                        iconOrImageModifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(color = forestGreen.invoke()),
                        maxLines = 10,
                        textColor = forestGreen.invoke()
                    )
                }
            }

            VerticalSpace(space = VeryLowSpacing)

            Text(text = details.desc.fromHtml(), fontWeight = FontWeight.Light)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductImages(
    state: ProductListState,
    addToWishlist: () -> Unit,
) {
    val details = state.productDetails!!.result[0]
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

        val alreadyWishListed =
            if (state.wishListData!!.result == null || state.wishListData.result.isEmpty()) false
            else state.wishListData.result.any { (it.product.id ?: "") == (details.id ?: "") }

        CircleIconButton(
            isDrawableIcon = false,
            tint = if (alreadyWishListed) primaryColor.invoke() else LocalContentColor.current,
            icon = if (alreadyWishListed) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            modifier = Modifier
                .padding(LowPadding)
                .align(Alignment.TopEnd)
                .size(30.dp)
        ) { addToWishlist() }

        Column(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            if (isOnSale)
                SaleBanner(
                    text = "$rupeeSign ${details.price}",
                    textDecoration = TextDecoration.LineThrough,
                    backgroundColor = secondaryColor.invoke()
                )
            SaleBanner(text = "$rupeeSign ${if (isOnSale) details.sellingPrice else details.price}")
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
        modifier = modifier
            .clip(RoundedCornerShape(MediumRoundedCorners))
            .background(color = primaryColor.invoke())
            .padding(horizontal = MediumPadding, vertical = LowPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = textInCircleModifier
                .padding(horizontal = LowPadding)
                .noRippleClickable {
                    if (cartItemLength > 0)
                        onUpdateCart(cartItemLength - 1)
                },
            text = "-",
            color = screenBackgroundColor.invoke(),
            fontSize = textInCircleFontSize,
        )

        Text(
            text = cartItemLength.toString(),
            modifier = Modifier
                .padding(
                    horizontal = MediumPadding,
                ),
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            color = screenBackgroundColor.invoke()
        )

        Text(
            modifier = textInCircleModifier
                .padding(horizontal = LowPadding)
                .noRippleClickable {
                    onUpdateCart(cartItemLength + 1)
                },
            text = "+",
            color = screenBackgroundColor.invoke(),
            fontSize = textInCircleFontSize,
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