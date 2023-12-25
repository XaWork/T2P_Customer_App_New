package me.taste2plate.app.customer.presentation.screens.product

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.T2PApp
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.data.Taste
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.use_case.analytics.AddLogUseCase
import me.taste2plate.app.customer.domain.use_case.custom.CheckAvalibilityUseCase
import me.taste2plate.app.customer.domain.use_case.product.ProductBy
import me.taste2plate.app.customer.domain.use_case.product.ProductDetailsUseCase
import me.taste2plate.app.customer.domain.use_case.product.ProductListUseCase
import me.taste2plate.app.customer.domain.use_case.user.PostReviewUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.AddToCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.CartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.DeleteCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.UpdateCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.AddToWishlistUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.RemoveFromWishlistUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.WishlistUseCase
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens
import me.taste2plate.app.customer.presentation.screens.home.FoodItemUpdateInfo
import me.taste2plate.app.customer.presentation.screens.product.list.ProductEvents
import me.taste2plate.app.customer.presentation.screens.product.list.ProductListState
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productListUseCase: ProductListUseCase,
    private val productDetailsUseCase: ProductDetailsUseCase,
    private val cartUseCase: CartUseCase,
    private val removeFromWishlistUseCase: RemoveFromWishlistUseCase,
    private val wishlistUseCase: WishlistUseCase,
    private val addToWishlistUseCase: AddToWishlistUseCase,
    private val postReviewUseCase: PostReviewUseCase,
    private val addLogUseCase: AddLogUseCase,
    private val userPrefs: UserPref,
    private val checkAvalibilityUseCase: CheckAvalibilityUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    var state by mutableStateOf(ProductListState())
    var searchValue by mutableStateOf("")
    var selectedProductId = ""

    init {
        getTaste()
    }

    fun onEvent(event: ProductEvents) {
        when (event) {
            is ProductEvents.GetProducts -> {
                state = state.copy(itemInfo = event.itemInfo)
                val itemId = event.itemInfo.id
                when (event.itemInfo.type) {
                    CityBrandScreens.City.name -> {
                        getProductList(ProductBy.City, itemId)
                    }

                    CityBrandScreens.Brand.name -> {
                        getProductList(ProductBy.Brand, itemId)
                    }

                    CityBrandScreens.Cuisine.name -> {
                        getProductList(ProductBy.Cuisine, itemId)
                    }

                    CityBrandScreens.Category.name -> {
                        getProductList(ProductBy.Category, itemId)
                    }

                    CityBrandScreens.Search.name -> {
                        getProductList(ProductBy.Search, searchValue)
                    }

                    CityBrandScreens.Slider.name -> {
                        getProductList(ProductBy.Slider, itemId)
                    }


                }
            }

            is ProductEvents.ChangeTaste -> {
                setTaste()
            }

            is ProductEvents.AddLog -> {
                addLog(event.logRequest)
            }


            is ProductEvents.PostReview -> {
                postReview(event.rating, event.review)
            }

            is ProductEvents.AddToWishlist -> {
                val productId = event.productId
                val alreadyWishListed =
                    if (state.wishListData!!.result.isEmpty()) false else state.wishListData!!.result.any { it.product.id == productId }
                if (alreadyWishListed)
                    removeFromWishlist(productId)
                else
                    addToWishlist(event.productId)
            }

            is ProductEvents.CheckAvailibility -> {
                val zipCode = event.zip
                if (zipCode.length != 6)
                    state = state.copy(message = "Enter valid pincode")
                else
                    checkAvalibility(event.zip)
            }

            is ProductEvents.UpdateCart -> {
                Log.e("UpdateCart", "Product id ${event.productId} \n Quantity: ${event.quantity}")
                when {
                    event.quantity == 0 -> {
                        deleteCart(event.productId)
                    }

                    state.cartData != null && state.cartData!!.result != null && state.cartData!!.result.isNotEmpty() -> {
                        var itemInCart = false
                        state.cartData!!.result.forEach {
                            if (it.product.id == event.productId)
                                itemInCart = true
                        }

                        if (itemInCart) {
                            updateCart(
                                productId = event.productId,
                                quantity = event.quantity,
                                event.context
                            )
                        } else {
                            addToCart(event.productId, event.context)
                        }
                    }

                    else -> {
                        addToCart(event.productId, event.context)
                    }
                }
            }

            is ProductEvents.GetProductDetails -> {
                getProductDetails()
            }

            is ProductEvents.UpdateState -> {
                state = state.copy(isError = false, message = null, cartError = false)
            }
        }
    }


    private fun addLog(logRequest: LogRequest) {
        viewModelScope.launch {
            addLogUseCase.execute(logRequest)
        }
    }


    private fun setTaste() {
        viewModelScope.launch {
            userPrefs.setTaste()
            getTaste()
            onEvent(ProductEvents.GetProducts(state.itemInfo!!))
        }
    }

    private fun getTaste() {
        viewModelScope.launch {
            val taste = userPrefs.getTaste()
            val setting = userPrefs.getSettings()
            state = state.copy(checked = taste == Taste.nonVeg, settings = setting)
        }
    }

    private fun getDefaultAddress() {
        Log.e("tag", "Default address called")
        viewModelScope.launch {
            val address = userPrefs.getDefaultAddress()
            state = state.copy(defaultAddress = address)
            checkAvalibility(state.defaultAddress!!.pincode)
        }
    }

    private fun addToWishlist(productId: String) {
        viewModelScope.launch {
            addToWishlistUseCase.execute(
                productId
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(
                            foodItemUpdateInfo = FoodItemUpdateInfo(
                                id = productId,
                                isLoading = true
                            )
                        )
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        getWishlist()
                        if (!isError)
                            addLog(
                                LogRequest(
                                    type = LogType.addToWishlist,
                                    event = "Add to wishlist",
                                    page_name = "/productList",
                                    product_id = productId
                                )
                            )
                        state =
                            state.copy(
                                isLoading = false,
                                addToWishlistResponse = result.data,
                                message = result.data?.message,
                                isError = isError,
                                foodItemUpdateInfo = state.foodItemUpdateInfo?.copy(
                                    isLoading = false,
                                    added = result.data?.status == Status.success.name
                                )
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message,
                            foodItemUpdateInfo = state.foodItemUpdateInfo?.copy(
                                isLoading = false,
                                added = false
                            )
                        )
                    }
                }

            }
        }
    }


    private fun removeFromWishlist(
        productId: String
    ) {
        viewModelScope.launch {
            removeFromWishlistUseCase.execute(
                productId = productId
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(
                            foodItemUpdateInfo = FoodItemUpdateInfo(
                                id = productId,
                                isLoading = true
                            )
                        )
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        if (!isError) {
                            addLog(
                                LogRequest(
                                    type = LogType.actionPerform,
                                    event = "remove from wishlist",
                                    page_name = "/productList",
                                    product_id = productId
                                )
                            )
                        }
                        val data = result.data
                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            message = if (isError) data!!.message else null,
                            deleteFromWishlistModel = result.data,
                            foodItemUpdateInfo = state.foodItemUpdateInfo?.copy(
                                isLoading = false,
                                added = false
                            )
                        )

                        getWishlist()

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message,
                            foodItemUpdateInfo = state.foodItemUpdateInfo?.copy(
                                isLoading = false,
                                added = false
                            )
                        )
                    }
                }

            }
        }
    }

    private fun postReview(rating: Float, review: String) {
        viewModelScope.launch {
            postReviewUseCase.execute(
                productId = selectedProductId,
                rating = rating,
                review = review
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(
                            reviewLoading = true
                        )
                    }

                    is Resource.Success -> {
                        state =
                            state.copy(
                                reviewLoading = false,
                                postReviewResponse = result.data,
                                message = result.data?.message,
                                isError = result.data?.status == Status.error.name,
                            )
                        addLog(
                            LogRequest(
                                type = LogType.actionPerform,
                                event = "post review",
                                page_name = "/productDetails",
                                product_id = selectedProductId
                            )
                        )

                        getProductDetails()
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            reviewLoading = false,
                            isError = true,
                            message = result.message,
                        )
                    }
                }

            }
        }
    }

    private fun checkAvalibility(zipCode: String) {
        if (state.productDetails != null && state.productDetails?.result?.isNotEmpty() == true) {
            val vendorId = state.productDetails!!.result[0].vendor.id
            viewModelScope.launch {
                checkAvalibilityUseCase.execute(
                    zipCode,
                    vendorId
                ).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            state = state.copy(
                                buttonLoading = true
                            )
                        }

                        is Resource.Success -> {
                            val isError = result.data?.status == Status.error.name

                            if (!isError) {
                                addLog(
                                    LogRequest(
                                        type = LogType.actionPerform,
                                        event = "check availability",
                                        page_name = "/productList",
                                        product_id = selectedProductId
                                    )
                                )
                            }
                            state =
                                state.copy(
                                    buttonLoading = false,
                                    message = if (result.data?.message.isNullOrEmpty()) "Great!! We are servicing in you area." else result.data?.message,
                                    isError = isError,
                                    addToCartEnable = !isError,
                                    checkAvailabilityModel = result.data
                                )
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                isError = true,
                                message = result.message,
                            )
                        }
                    }

                }
            }
        } else {
            state = state.copy(isError = true, message = "Something went wrong. Please try again.")
        }
    }


    private fun getWishlist() {
        viewModelScope.launch {
            wishlistUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        //state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        val data = result.data

                        state = state.copy(
                            // isLoading = false,
                            wishListData = data,
                            // isError = isError,
                            message = null,
                        )

                        getCart()
                        T2PApp.wishlistCount = data?.result?.size ?: 0

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }

            }
        }
    }

    private fun getCart() {
        viewModelScope.launch {
            cartUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        //state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        if (result.data?.status == Status.success.name)
                            T2PApp.cartCount = result.data.result.size
                        state =
                            state.copy(
                                isLoading = false,
                                cartData = result.data,
                                //isError = isError,
                            )

                        if (selectedProductId.isNotEmpty() && state.defaultAddress == null)
                            getDefaultAddress()

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isError = true,
                        )
                    }
                }

            }
        }
    }


    private fun updateCart(
        productId: String,
        quantity: Int,
        context: Context
    ) {
        viewModelScope.launch {
            updateCartUseCase.execute(
                productId, quantity
            ).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy()
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name



                        if (!isError) {
                            addAppEvent(context, productId, quantity)
                            addLog(
                                LogRequest(
                                    type = LogType.addToCart,
                                    event = "update Cart",
                                    page_name = "/productList",
                                    product_id = productId
                                )
                            )
                        }

                        getCart()

                        state.copy(
                            // isLoading = false,
                            isError = isError,
                            message = data!!.message,
                        )

                    }

                    is Resource.Error ->
                        state.copy(
                            //isLoading = false,
                            isError = true,
                        )
                }

            }
        }
    }

    private fun deleteCart(
        productId: String,
    ) {
        viewModelScope.launch {
            deleteCartUseCase.execute(
                productId
            ).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy()
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name
                        getCart()

                        if (!isError)
                            addLog(
                                LogRequest(
                                    type = LogType.actionPerform,
                                    event = "delete Cart",
                                    page_name = "/productList",
                                    product_id = productId
                                )
                            )

                        state.copy(
                            // isLoading = false,
                            isError = isError,
                            message = data!!.message
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            //isLoading = false,
                            isError = true,
                        )
                }

            }
        }
    }


    private fun addToCart(productId: String, context: Context) {
        viewModelScope.launch {
            addToCartUseCase.execute(
                productId
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        getCart()
                        val isError = result.data?.status == Status.error.name

                        if (!isError) {
                            addAppEvent(context, productId, 1)
                            addLog(
                                LogRequest(
                                    type = LogType.addToCart,
                                    event = "Add to cart",
                                    page_name = "/home",
                                    product_id = productId
                                )
                            )
                        }
                        state =
                            state.copy(
                                message = result.data?.message,
                                cartError = isError
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isError = true,
                        )
                    }
                }

            }
        }
    }

    private fun getProductList(
        productBy: ProductBy,
        id: String
    ) {
        viewModelScope.launch {
            productListUseCase.execute(productBy, id).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data!!
                        val isError = data.status == Status.error.name
                        state =
                            state.copy(
                                //isLoading = false,
                                isError = isError,
                                message = if (isError) result.data.message else null,
                                productList = data.result.ifEmpty { emptyList() }
                            )
                        getWishlist()
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }
            }
        }
    }

    private fun getProductDetails() {
        viewModelScope.launch {
            productDetailsUseCase.execute(selectedProductId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data!!
                        val isError = data.status == Status.error.name
                        state =
                            state.copy(
                                //  isLoading = false,
                                isError = isError,
                                message = if (isError) result.data.message else null,
                                productDetails = data
                            )
                        getWishlist()
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }
            }
        }
    }

    private fun addAppEvent(context: Context, productId: String, quantity: Int) {
        //facebbook
        val logger = AppEventsLogger.newLogger(context)
        val params = Bundle()

        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "INR");
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, productId);
        params.putString(AppEventsConstants.EVENT_PARAM_NUM_ITEMS, quantity.toString());

        logger.logEvent(
            AppEventsConstants.EVENT_NAME_ADDED_TO_CART,
            params
        )
    }

}