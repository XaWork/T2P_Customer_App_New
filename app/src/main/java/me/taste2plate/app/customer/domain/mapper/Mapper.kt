package me.taste2plate.app.customer.domain.mapper

import me.taste2plate.app.customer.domain.model.Category
import me.taste2plate.app.customer.domain.model.CityBrandModel
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.SubCategoryModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.WishListModel

fun WishListModel.Result.toCommonForWishAndCartItem(): CommonForItem {
    return CommonForItem(
        id = product.id,
        image = product.file[0].location,
        name = product.name,
        price = product.price.toString()
    )

}

fun CartModel.Result.toCommonForWishAndCartItem(): CommonForItem {
    return CommonForItem(
        id = product.id,
        image = product.file[0].location,
        name = product.name,
        quantity = quantity,
        price = product.price
    )

}


data class CommonForItem(
    val id: String,
    val image: String,
    val name: String,
    val description: String? = null,
    val quantity: Int? = null,
    val price: String? = null,
    val type: String? = null
)

//-------------------------------

fun HomeModel.Cuisine.toCommonItem(): CommonForItem {
    return CommonForItem(
        id = id,
        image = file,
        name = name,
        description = description
    )
}


fun CityBrandModel.Result.toCommonItem(): CommonForItem {
    return CommonForItem(
        id = id,
        image = file,
        name = name,
        description = if (description == null || description.isEmpty()) desc else description
    )
}


fun Category.toCommonItem(): CommonForItem {
    return CommonForItem(
        id = id,
        image = file,
        name = name,
        description = description
    )
}


fun SubCategoryModel.Result.toCommonItem(): CommonForItem {
    return CommonForItem(
        id = id,
        image = file,
        name = name,
        description = description
    )
}

