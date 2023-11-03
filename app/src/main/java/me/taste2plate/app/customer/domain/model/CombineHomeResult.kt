package me.taste2plate.app.customer.domain.model

import me.taste2plate.app.customer.data.Resource

data class CombinedResult(
    val homeData: Resource<HomeModel>,
    val wishlistData: Resource<HomeModel>? = null,
    val cartListData: Resource<HomeModel>? = null
)
