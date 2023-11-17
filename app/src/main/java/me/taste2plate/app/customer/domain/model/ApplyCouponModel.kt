package me.taste2plate.app.customer.domain.model

import com.google.gson.annotations.SerializedName
import me.taste2plate.app.customer.domain.model.user.Gst
import me.taste2plate.app.customer.domain.model.user.GstWithPoint
import me.taste2plate.app.customer.domain.model.user.NewFinalPrice
import me.taste2plate.app.customer.domain.model.user.Shipping


data class ApplyCouponModel(
    val cartprice: Float,
    val coupon_discount: Float,
    val coupon_type: String,
    val final_price: Float,
    val message: String,
    val shipping_weight: Float,
    val status: String,
    val total_cgst: Float,
    val total_igst: Float,
    val total_packing_price: Float,
    val total_sgst: Float,
    val gst: Gst,
    @SerializedName("gst_with_point")
    val gstWithWallet: GstWithPoint,
    val shipping: Shipping,
    val new_final_price: NewFinalPrice
)