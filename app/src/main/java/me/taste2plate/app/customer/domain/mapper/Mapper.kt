package me.taste2plate.app.customer.domain.mapper

import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.WishListModel

fun WishListModel.Result.toCommonForWishAndCartItem(): CommonForWishAndCartItem {
    return CommonForWishAndCartItem(
        id = product.id,
        image = product.file[0].location,
        name = product.name,
        price = product.price.toString()
    )

}

fun CartModel.Result.toCommonForWishAndCartItem(): CommonForWishAndCartItem {
    return CommonForWishAndCartItem(
        id = product.id,
        image = product.file[0].location,
        name = product.name,
        quantity = quantity,
        price = product.price.toString()
    )

}

data class CommonForWishAndCartItem(
    val id: String,
    val image: String,
    val name: String,
    val quantity: Int? = null,
    val price: String
)