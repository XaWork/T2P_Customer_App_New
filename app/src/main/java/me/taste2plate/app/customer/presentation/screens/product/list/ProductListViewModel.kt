package me.taste2plate.app.customer.presentation.screens.product.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.domain.use_case.product.ProductBy
import me.taste2plate.app.customer.domain.use_case.product.ProductListUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.DeleteCartUseCase
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productListUseCase: ProductListUseCase,
) : ViewModel() {

    var state by mutableStateOf(ProductListState())

    fun onEvent(event: ProductListEvents) {
        when (event) {
            is ProductListEvents.GetProducts -> {
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
                        //    getProductList(ProductBy.City, itemId)
                    }

                    "slider" -> {}
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


}