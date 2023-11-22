package me.taste2plate.app.customer.domain.model.user


import com.google.gson.annotations.SerializedName

data class OrderUpdateModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("position")
    val position: Position,
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("status")
    val status: String
) {
    class Position

    data class Result(
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("note")
        val note: String,
        @SerializedName("order")
        val order: String,
        @SerializedName("__v")
        val v: Int
    )
}