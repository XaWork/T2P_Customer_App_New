package me.taste2plate.app.customer.domain.model.auth

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("aadhar")
    val aadhar: String,
    @SerializedName("about")
    val about: String,
    @SerializedName("acc_holder_name")
    val accHolderName: Any,
    @SerializedName("acc_number")
    val accNumber: Any,
    @SerializedName("active")
    val active: Int,
    @SerializedName("additional_cost1")
    val additionalCost1: Int,
    @SerializedName("additional_cost2")
    val additionalCost2: Int,
    @SerializedName("address")
    val address: String,
    @SerializedName("bag_no")
    val bagNo: List<Any>,
    @SerializedName("bank_address")
    val bankAddress: Any,
    @SerializedName("bank_name")
    val bankName: Any,
    @SerializedName("branch_code")
    val branchCode: Any,
    @SerializedName("cargo_position")
    val cargoPosition: CargoPosition,
    @SerializedName("city")
    val city: Any,
    @SerializedName("city2")
    val city2: String,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("cod_order_cost")
    val codOrderCost: Int,
    @SerializedName("commission")
    val commission: String,
    @SerializedName("commission_type")
    val commissionType: String,
    @SerializedName("communication_zipcode")
    val communicationZipcode: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("customer_support_city")
    val customerSupportCity: List<Any>,
    @SerializedName("date_of_join")
    val dateOfJoin: Any,
    @SerializedName("date_of_releiving")
    val dateOfReleiving: Any,
    @SerializedName("deleted")
    val deleted: Int,
    @SerializedName("delivery_boy")
    val deliveryBoy: List<Any>,
    @SerializedName("delivery_city")
    val deliveryCity: List<Any>,
    @SerializedName("delivery_partner_position")
    val deliveryPartnerPosition: DeliveryPartnerPosition,
    @SerializedName("device_token")
    val deviceToken: String,
    @SerializedName("device_type")
    val deviceType: String,
    @SerializedName("doc1")
    val doc1: String,
    @SerializedName("doc1_type")
    val doc1Type: String,
    @SerializedName("doc2")
    val doc2: String,
    @SerializedName("doc2_type")
    val doc2Type: String,
    @SerializedName("doc3")
    val doc3: String,
    @SerializedName("doc3_type")
    val doc3Type: String,
    @SerializedName("email")
    val email: String?,
    @SerializedName("email_otp")
    val emailOtp: Int,
    @SerializedName("father_name")
    val fatherName: String,
    @SerializedName("file")
    val `file`: String,
    @SerializedName("first_time")
    val firstTime: Int,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("health_insurance_number")
    val healthInsuranceNumber: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("ifsc")
    val ifsc: Any,
    @SerializedName("login_active")
    val loginActive: Int,
    @SerializedName("lp_manager")
    val lpManager: List<Any>,
    @SerializedName("master")
    val master: Any,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("monthly_fixed_cost")
    val monthlyFixedCost: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("office")
    val office: Any,
    @SerializedName("otp")
    val otp: Int,
    @SerializedName("pan")
    val pan: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("post")
    val post: String,
    @SerializedName("present_address")
    val presentAddress: String,
    @SerializedName("price_per_kg")
    val pricePerKg: String,
    @SerializedName("price_per_km_without_order")
    val pricePerKmWithoutOrder: String,
    @SerializedName("price_per_pack")
    val pricePerPack: String,
    @SerializedName("profile_image")
    val profileImage: String,
    @SerializedName("rate_per_km")
    val ratePerKm: String,
    @SerializedName("reffer_by")
    val refferBy: String,
    @SerializedName("role")
    val role: Any,
    @SerializedName("salary")
    val salary: String,
    @SerializedName("seo_desc")
    val seoDesc: String,
    @SerializedName("seo_title")
    val seoTitle: String,
    @SerializedName("service_zipcode")
    val serviceZipcode: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("subscription")
    val subscription: Subscription,
    @SerializedName("update_date")
    val updateDate: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("user_type")
    val userType: String,
    @SerializedName("__v")
    val v: Int,
    @SerializedName("vendor_position")
    val vendorPosition: VendorPosition,
    @SerializedName("zipcode")
    val zipcode: String
) {
    data class CargoPosition(
        @SerializedName("coordinates")
        val coordinates: List<Int>,
        @SerializedName("type")
        val type: String
    )

    data class DeliveryPartnerPosition(
        @SerializedName("coordinates")
        val coordinates: List<Int>,
        @SerializedName("type")
        val type: String
    )

    data class Subscription(
        @SerializedName("exp_date")
        val expDate: Any,
        @SerializedName("key")
        val key: String,
        @SerializedName("plan")
        val plan: Any,
        @SerializedName("point")
        val point: Int,
        @SerializedName("updated")
        val updated: Any
    )

    data class VendorPosition(
        @SerializedName("coordinates")
        val coordinates: List<Int>,
        @SerializedName("type")
        val type: String
    )
}