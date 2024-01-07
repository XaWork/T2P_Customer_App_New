package me.taste2plate.app.customer.domain.model.user


import com.google.gson.annotations.SerializedName

data class GharKaKhanaCheckoutModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("express_remarks")
    val expressRemarks: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("normal_remarks")
    val normalRemarks: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("active")
        val active: Int,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("delivery_boy")
        val deliveryBoy: Any?,
        @SerializedName("delivery_date")
        val deliveryDate: String,
        @SerializedName("delivery_distance")
        val deliveryDistance: String,
        @SerializedName("delivery_partner")
        val deliveryPartner: Any?,
        @SerializedName("delivery_price")
        val deliveryPrice: String,
        @SerializedName("delivery_timeslot")
        val deliveryTimeslot: String,
        @SerializedName("delivery_type")
        val deliveryType: String,
        @SerializedName("destination_location")
        val destinationLocation: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("order_confirmation")
        val orderConfirmation: Int,
        @SerializedName("orderid")
        val orderid: String,
        @SerializedName("pickup_boy")
        val pickupBoy: Any?,
        @SerializedName("pickup_date")
        val pickupDate: String,
        @SerializedName("pickup_distance")
        val pickupDistance: String,
        @SerializedName("pickup_partner")
        val pickupPartner: Any?,
        @SerializedName("pickup_price")
        val pickupPrice: String,
        @SerializedName("pickup_time")
        val pickupTime: String,
        @SerializedName("products")
        val products: List<Any>,
        @SerializedName("remarks")
        val remarks: String,
        @SerializedName("source_location")
        val sourceLocation: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("total_price")
        val totalPrice: String,
        @SerializedName("total_weight")
        val totalWeight: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("user")
        val user: String,
        @SerializedName("__v")
        val v: Int
    )
}