package me.taste2plate.app.customer.presentation.screens.wishlist

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
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.use_case.user.wishlist.RemoveFromWishlistUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.WishlistUseCase
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistUseCase: WishlistUseCase,
    private val removeFromWishlistUseCase: RemoveFromWishlistUseCase,
    private val userPref: UserPref
) : ViewModel() {

    var state by mutableStateOf(WishlistState())

    fun onEvent(event: WishlistEvents) {
        when (event) {
            is WishlistEvents.GetWishlist -> getWishlist()
            is WishlistEvents.UpdateState -> {
                state = state.copy(normalMessage = null)
            }

            is WishlistEvents.RemoveFromWishlist -> removeFromWishlist(event.productId)
        }
    }


    private fun getWishlist(isLoading: Boolean = true) {
        viewModelScope.launch {
            wishlistUseCase.execute(
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = isLoading)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            isError = result.data?.status == Status.error.name,
                            errorMessage = "Something went wrong",
                            wishlist = result.data
                        )

                        if (result.data != null && result.data.result.isNotEmpty())
                            T2PApp.wishlistCount = result.data.result.size

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
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
                        // state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            isError = result.data?.status == Status.error.name,
                            errorMessage = "Something went wrong",
                            normalMessage = result.data?.message,
                            deleteFromWishlistModel = result.data
                        )

                        getWishlist(isLoading = false)

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                    }
                }

            }
        }
    }

}