package me.taste2plate.app.customer.domain.model


import com.google.gson.annotations.SerializedName

data class ZipListModel(
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("status")
    val status: String
) {
    data class Result(
        @SerializedName("active")
        val active: Int,
        @SerializedName("additional_cost")
        val additionalCost: Int,
        @SerializedName("city")
        val city: String,
        @SerializedName("cod")
        val cod: Boolean,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("express")
        val express: Boolean,
        @SerializedName("express_cod")
        val expressCod: Boolean,
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("timeslot")
        val timeslot: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )
}