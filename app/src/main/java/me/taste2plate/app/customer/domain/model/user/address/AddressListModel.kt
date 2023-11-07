package me.taste2plate.app.customer.domain.model.user.address


import com.google.gson.annotations.SerializedName

data class AddressListModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("status")
    val status: String
) {
    data class Result(
        @SerializedName("address")
        val address: String,
        @SerializedName("address2")
        val address2: String,
        @SerializedName("city")
        val city: City,
        @SerializedName("contact_mobile")
        val contactMobile: String,
        @SerializedName("contact_name")
        val contactName: String,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("_id")
        val id: String,
        @SerializedName("landmark")
        val landmark: String,
        @SerializedName("pincode")
        val pincode: String,
        @SerializedName("position")
        val position: Position,
        @SerializedName("post_office")
        val postOffice: String,
        @SerializedName("state")
        val state: State,
        @SerializedName("title")
        val title: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("user")
        val user: String,
        @SerializedName("__v")
        val v: Int
    ) {
        data class City(
            @SerializedName("_id")
            val id: String,
            @SerializedName("name")
            val name: String
        )

        data class Position(
            @SerializedName("coordinates")
            val coordinates: List<Double>,
            @SerializedName("type")
            val type: String
        )

        data class State(
            @SerializedName("_id")
            val id: String,
            @SerializedName("name")
            val name: String
        )
    }
}