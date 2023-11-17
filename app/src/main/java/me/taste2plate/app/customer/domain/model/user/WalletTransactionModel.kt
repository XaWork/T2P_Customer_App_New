package me.taste2plate.app.customer.domain.model.user


import com.google.gson.annotations.SerializedName

data class WalletTransactionModel(
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalCount")
    val totalCount: Int
) {
    data class Result(
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("note")
        val note: String,
        @SerializedName("point")
        val point: Int,
        @SerializedName("type")
        val type: Int,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("user")
        val user: String,
        @SerializedName("__v")
        val v: Int
    )
}