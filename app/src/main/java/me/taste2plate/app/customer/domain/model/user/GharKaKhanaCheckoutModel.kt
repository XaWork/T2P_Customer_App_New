package me.taste2plate.app.customer.domain.model.user


import com.google.gson.annotations.SerializedName

data class GharKaKhanaCheckoutModel(
    @SerializedName("cgst")
    val cgst: String?,
    @SerializedName("delivery_date")
    val deliveryDate: String?,
    @SerializedName("delivery_distance")
    val deliveryDistance: String?,
    @SerializedName("delivery_free_distance")
    val deliveryFreeDistance: String?,
    @SerializedName("delivery_price")
    val deliveryPrice: String?,
    @SerializedName("delivery_timeslot")
    val deliveryTimeslot: String?,
    @SerializedName("igst")
    val igst: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("multiplier_for_delivery")
    val multiplierForDelivery: String?,
    @SerializedName("multiplier_for_pickup")
    val multiplierForPickup: String?,
    @SerializedName("pick_up_free_distance")
    val pickUpFreeDistance: String?,
    @SerializedName("pickup_distance")
    val pickupDistance: String?,
    @SerializedName("pickup_price")
    val pickupPrice: String?,
    @SerializedName("result")
    val result: List<Result?>?,
    @SerializedName("sgst")
    val sgst: String?,
    @SerializedName("shipping_price")
    val shippingPrice: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("total_price")
    val totalPrice: String?,
    @SerializedName("total_weight")
    val totalWeight: String?
) {
    data class Result(
        @SerializedName("category")
        val category: String?,
        @SerializedName("createdAt")
        val createdAt: String?,
        @SerializedName("height")
        val height: String?,
        @SerializedName("_id")
        val id: String?,
        @SerializedName("length")
        val length: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("price")
        val price: String?,
        @SerializedName("sub_category")
        val subCategory: String?,
        @SerializedName("updatedAt")
        val updatedAt: String?,
        @SerializedName("user")
        val user: String?,
        @SerializedName("__v")
        val v: Int?,
        @SerializedName("weidth")
        val weidth: String?,
        @SerializedName("weight")
        val weight: String?
    )
}