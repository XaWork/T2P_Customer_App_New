package me.taste2plate.app.customer.presentation.screens.cart

import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse

data class CheckoutState(
    val isLoading:Boolean = false,
    val isError:Boolean = false,
    val errorMessage: String? = null,
    val normalMessage: String? = null,
    val cart : CartModel? = null,
    val updateCartResponse : CommonResponse? = null
)
