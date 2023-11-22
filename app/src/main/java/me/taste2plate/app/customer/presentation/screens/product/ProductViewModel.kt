package me.taste2plate.app.customer.presentation.screens.product

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.T2PApp
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.data.Taste
import me.taste2plate.app.customer.domain.use_case.product.ProductBy
import me.taste2plate.app.customer.domain.use_case.product.ProductDetailsUseCase
import me.taste2plate.app.customer.domain.use_case.product.ProductListUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.AddToCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.CartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.DeleteCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.UpdateCartUseCase
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens
import me.taste2plate.app.customer.presentation.screens.product.list.ProductEvents
import me.taste2plate.app.customer.presentation.screens.product.list.ProductListState
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productListUseCase: ProductListUseCase,
    private val productDetailsUseCase: ProductDetailsUseCase,
    private val cartUseCase: CartUseCase,
    private val userPrefs: UserPref,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    var state by mutableStateOf(ProductListState())
    var selectedProductId = ""

    init {
        getTaste()
        getCart()
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

                    "slider" -> {
                        getProductList(ProductBy.Slider, itemId)
                    }
                }
            }

            is ProductEvents.ChangeTaste -> {
                setTaste()
            }

            is ProductEvents.UpdateCart -> {
                Log.e("UpdateCart", "Product id ${event.productId} \n Quantity: ${event.quantity}")
                when {
                    event.quantity == 0 -> {
                        deleteCart(event.productId)
                    }

                    state.cartData != null && state.cartData!!.result.isNotEmpty() -> {
                        var itemInCart = false
                        state.cartData!!.result.forEach {
                            if (it.product.id == event.productId)
                                itemInCart = true
                        }

                        if (itemInCart) {
                            updateCart(productId = event.productId, quantity = event.quantity)
                        } else {
                            addToCart(event.productId)
                        }
                    }

                    else -> {
                        addToCart(event.productId)
                    }
                }
            }

            is ProductEvents.GetProductDetails -> {
                getProductDetails()
            }
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
            state = state.copy(checked = taste == Taste.nonVeg)
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
                        val isError = result.data?.status == Status.error.name
                        if (!isError)
                            T2PApp.cartCount = result.data!!.result.size
                        state =
                            state.copy(
                                isLoading = false,
                                cartData = result.data,
                                isError = isError,
                            )

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
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
                                isLoading = false,
                                isError = isError,
                                message = if (isError) result.data.message else "",
                                productList = data.result.ifEmpty { emptyList() }
                            )
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

    private fun updateCart(
        productId: String,
        quantity: Int
    ) {
        viewModelScope.launch {
            updateCartUseCase.execute(
                productId, quantity
            ).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name
                        getCart()

                        state.copy(
                            isLoading = false,
                            isError = isError,
                            //errorMessage = "Something went Wrong",
                        )

                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
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
                    is Resource.Loading -> state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name
                        getCart()

                        state.copy(
                            isLoading = false,
                            isError = isError,
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                        )
                }

            }
        }
    }


    private fun addToCart(productId: String) {
        viewModelScope.launch {
            addToCartUseCase.execute(
                productId
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        getCart()
                        state =
                            state.copy(
                                isLoading = false,
                                message = result.data?.message,
                                isError = result.data?.status == Status.error.name,
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
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
                                isLoading = false,
                                isError = isError,
                                message = if (isError) result.data.message else "",
                                productDetails = data
                            )
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


}