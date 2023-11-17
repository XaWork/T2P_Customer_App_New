package me.taste2plate.app.customer.domain.model


import com.google.gson.annotations.SerializedName
import me.taste2plate.app.customer.domain.model.custom.City

data class CityListModel(
    @SerializedName("result")
    val result: List<City>,
    @SerializedName("status")
    val status: String
)