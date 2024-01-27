package me.taste2plate.app.customer.domain.model.user


import com.google.gson.annotations.SerializedName

data class GharKaKhanaOrderList(
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: List<Result?>?,
    @SerializedName("status")
    val status: String?
) {
    data class Result(
        @SerializedName("active")
        val active: Int?,
        @SerializedName("cgst")
        val cgst: String?,
        @SerializedName("createdAt")
        val createdAt: String?,
        @SerializedName("deleted")
        val deleted: Int?,
        @SerializedName("delivery_boy")
        val deliveryBoy: String?,
        @SerializedName("delivery_date")
        val deliveryDate: String?,
        @SerializedName("delivery_distance")
        val deliveryDistance: String?,
        @SerializedName("delivery_partner")
        val deliveryPartner: String?,
        @SerializedName("delivery_price")
        val deliveryPrice: String?,
        @SerializedName("delivery_timeslot")
        val deliveryTimeslot: String?,
        @SerializedName("delivery_type")
        val deliveryType: String?,
        @SerializedName("destination_location")
        val destinationLocation: String?,
        @SerializedName("_id")
        val id: String?,
        @SerializedName("igst")
        val igst: String?,
        @SerializedName("order_confirmation")
        val orderConfirmation: Int?,
        @SerializedName("orderid")
        val orderid: String?,
        @SerializedName("pickup_boy")
        val pickupBoy: String?,
        @SerializedName("pickup_date")
        val pickupDate: String?,
        @SerializedName("pickup_distance")
        val pickupDistance: String?,
        @SerializedName("pickup_partner")
        val pickupPartner: String?,
        @SerializedName("pickup_price")
        val pickupPrice: String?,
        @SerializedName("pickup_time")
        val pickupTime: String?,
        @SerializedName("products")
        val products: List<Product?>?,
        @SerializedName("remarks")
        val remarks: String?,
        @SerializedName("sgst")
        val sgst: String?,
        @SerializedName("source_location")
        val sourceLocation: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("total_price")
        val totalPrice: String?,
        @SerializedName("total_weight")
        val totalWeight: String?,
        @SerializedName("updatedAt")
        val updatedAt: String?,
        @SerializedName("user")
        val user: String?,
        @SerializedName("__v")
        val v: Int?
    ) {
        data class Product(
            @SerializedName("category")
            val category: String?,
            @SerializedName("height")
            val height: String?,
            @SerializedName("_id")
            val id: String?,
            @SerializedName("length")
            val length: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("sub_category")
            val subCategory: String?,
            @SerializedName("weidth")
            val weidth: String?,
            @SerializedName("weight")
            val weight: String?
        )
    }
}