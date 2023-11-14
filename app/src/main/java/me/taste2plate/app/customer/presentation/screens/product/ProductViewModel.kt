package me.taste2plate.app.customer.presentation.screens.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.data.Taste
import me.taste2plate.app.customer.domain.use_case.product.ProductBy
import me.taste2plate.app.customer.domain.use_case.product.ProductDetailsUseCase
import me.taste2plate.app.customer.domain.use_case.product.ProductListUseCase
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens
import me.taste2plate.app.customer.presentation.screens.product.list.ProductEvents
import me.taste2plate.app.customer.presentation.screens.product.list.ProductListState
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productListUseCase: ProductListUseCase,
    private val productDetailsUseCase: ProductDetailsUseCase,
    private val userPrefs: UserPref
) : ViewModel() {

    var state by mutableStateOf(ProductListState())
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

                    "slider" -> {}
                }
            }

            is ProductEvents.ChangeTaste -> {
                setTaste()
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