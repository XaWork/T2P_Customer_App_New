package me.taste2plate.app.customer.presentation.screens.citybrand

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.mapper.toCommonItem
import me.taste2plate.app.customer.domain.use_case.BrandListUseCase
import me.taste2plate.app.customer.domain.use_case.CategoryUseCase
import me.taste2plate.app.customer.domain.use_case.CityListUseCase
import me.taste2plate.app.customer.domain.use_case.CuisineUseCase
import me.taste2plate.app.customer.domain.use_case.SubCategoryUseCase
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens
import javax.inject.Inject

@HiltViewModel
class CityBrandViewModel @Inject constructor(
    private val cityListUseCase: CityListUseCase,
    private val brandListUseCase: BrandListUseCase,
    private val cuisineUseCase: CuisineUseCase,
    private val categoryUseCase: CategoryUseCase,
    private val subCategoryUseCase: SubCategoryUseCase
) : ViewModel() {

    var state by mutableStateOf(CityBrandState())
    lateinit var selectedItem: CommonForItem

    fun onEvent(event: CityBrandEvents) {
        when (event) {
            is CityBrandEvents.GetData -> {
                when (event.screen) {
                    CityBrandScreens.City -> {
                        if (state.itemList.isEmpty())
                            getCityList()
                    }

                    CityBrandScreens.Category -> {
                        if (state.itemList.isEmpty())
                            getAllCategories()
                    }

                    CityBrandScreens.Brand -> {
                        if (state.itemList.isEmpty())
                            getBrandList()
                    }

                    else -> {
                        if (state.itemList.isEmpty())
                            getCuisine()
                    }
                }
            }

            is CityBrandEvents.SetSelectedItem -> {
                selectedItem = event.item
            }

            is CityBrandEvents.GetSubCategory -> {
                getAllSubCategories()
            }
        }
    }

    private fun getCityList() {
        viewModelScope.launch {
            cityListUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data!!
                        val isError = data.status == Status.error.name || data.result.isEmpty()

                        val itemList =
                            if (data.result.isEmpty()) emptyList() else data.result.map { it.toCommonItem() }

                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            itemList = itemList
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

    private fun getBrandList() {
        viewModelScope.launch {
            brandListUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data!!
                        val isError = data.status == Status.error.name || data.result.isEmpty()

                        val itemList =
                            if (data.result.isEmpty()) emptyList() else data.result.map { it.toCommonItem() }

                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            itemList = itemList
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


    private fun getCuisine() {
        viewModelScope.launch {
            cuisineUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data!!
                        val isError = data.status == Status.error.name || data.cuisine.isEmpty()

                        val itemList =
                            if (data.cuisine.isEmpty()) emptyList() else data.cuisine.map { it.toCommonItem() }

                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            itemList = itemList
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

    private fun getAllCategories() {
        viewModelScope.launch {
            categoryUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data!!
                        val isError = data.status == Status.error.name || data.result.isEmpty()

                        val itemList =
                            if (data.result.isEmpty()) emptyList() else data.result.map { it.toCommonItem() }

                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            itemList = itemList
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

    private fun getAllSubCategories() {
        viewModelScope.launch {
            subCategoryUseCase.execute(selectedItem.id).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data!!
                        val isError = data.status == Status.error.name || data.result.isEmpty()

                        val itemList =
                            if (data.result.isEmpty()) emptyList() else data.result.map { it.toCommonItem() }

                        state = state.copy(
                            isLoading = false,
                            showSubCategories = true,
                            isError = isError,
                            subCategoryItemList = itemList
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