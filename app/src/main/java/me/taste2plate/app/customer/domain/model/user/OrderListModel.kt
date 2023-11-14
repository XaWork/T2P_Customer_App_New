package me.taste2plate.app.customer.domain.model.user


import com.google.gson.annotations.SerializedName

data class OrderListModel(
    @SerializedName("cancel_time")
    val cancelTime: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("server_time")
    val serverTime: String,
    @SerializedName("status")
    val status: String
) {
    data class Result(
        @SerializedName("additional_cost")
        val additionalCost: String,
        @SerializedName("address")
        val address: Address,
        @SerializedName("bag")
        val bag: String,
        @SerializedName("box_id")
        val boxId: Any,
        @SerializedName("browser")
        val browser: String,
        @SerializedName("bundle_id")
        val bundleId: Any,
        @SerializedName("bundle_qr")
        val bundleQr: Int,
        @SerializedName("cargo_partner")
        val cargoPartner: CargoPartner,
        @SerializedName("cargo_partner_commission")
        val cargoPartnerCommission: Int,
        @SerializedName("cargo_position")
        val cargoPosition: CargoPosition,
        @SerializedName("cod_collected")
        val codCollected: String,
        @SerializedName("complementary")
        val complementary: Int,
        @SerializedName("coupon")
        val coupon: String,
        @SerializedName("couponamount")
        val couponamount: String,
        @SerializedName("coupontype")
        val coupontype: String,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("customer_order_status")
        val customerOrderStatus: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("delivery_boy")
        val deliveryBoy: Any,
        @SerializedName("delivery_date")
        val deliveryDate: String,
        @SerializedName("delivery_distance")
        val deliveryDistance: Int,
        @SerializedName("delivery_partner")
        val deliveryPartner: DeliveryPartner,
        @SerializedName("delivery_partner_commission")
        val deliveryPartnerCommission: Double,
        @SerializedName("delivery_partner_position")
        val deliveryPartnerPosition: DeliveryPartnerPosition,
        @SerializedName("delivery_weight")
        val deliveryWeight: String,
        @SerializedName("distance")
        val distance: String,
        @SerializedName("express")
        val express: String,
        @SerializedName("finalprice")
        val finalprice: String,
        @SerializedName("gateway")
        val gateway: String,
        @SerializedName("has_note")
        val hasNote: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("lp_head")
        val lpHead: Any,
        @SerializedName("lp_manager")
        val lpManager: Any,
        @SerializedName("order_city")
        val orderCity: String,
        @SerializedName("order_count")
        val orderCount: Int,
        @SerializedName("orderid")
        val orderid: String,
        @SerializedName("otp")
        val otp: String,
        @SerializedName("paid")
        val paid: Int,
        @SerializedName("partial_transaction")
        val partialTransaction: String,
        @SerializedName("partial_transaction_amount")
        val partialTransactionAmount: String,
        @SerializedName("pickup_boy")
        val pickupBoy: Any,
        @SerializedName("pickup_distance")
        val pickupDistance: Int,
        @SerializedName("pickup_partner")
        val pickupPartner: PickupPartner,
        @SerializedName("pickup_partner_commission")
        val pickupPartnerCommission: Int,
        @SerializedName("pickup_weight")
        val pickupWeight: String,
        @SerializedName("position")
        val position: Position,
        @SerializedName("price")
        val price: String,
        @SerializedName("products")
        val products: List<Product>,
        @SerializedName("return_reason")
        val returnReason: String,
        @SerializedName("schedule_date")
        val scheduleDate: String,
        @SerializedName("schedule_status")
        val scheduleStatus: String,
        @SerializedName("schedule_status_to")
        val scheduleStatusTo: String,
        @SerializedName("schedule_time")
        val scheduleTime: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("timeslot")
        val timeslot: String,
        @SerializedName("tip_price")
        val tipPrice: String,
        @SerializedName("totalCGST")
        val totalCGST: String,
        @SerializedName("totalIGST")
        val totalIGST: String,
        @SerializedName("totalPackingPrice")
        val totalPackingPrice: String,
        @SerializedName("totalSGST")
        val totalSGST: String,
        @SerializedName("totalShippingPrice")
        val totalShippingPrice: String,
        @SerializedName("total_weight")
        val totalWeight: String,
        @SerializedName("transactionid")
        val transactionid: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("user")
        val user: User,
        @SerializedName("__v")
        val v: Int,
        @SerializedName("vendor")
        val vendor: Vendor,
        @SerializedName("vendor_city")
        val vendorCity: String,
        @SerializedName("vendor_position")
        val vendorPosition: VendorPosition,
        @SerializedName("wallet_discount")
        val walletDiscount: String
    ) {
        data class Address(
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
            val postOffice: Any,
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

        data class CargoPartner(
            @SerializedName("aadhar")
            val aadhar: String,
            @SerializedName("about")
            val about: String,
            @SerializedName("acc_holder_name")
            val accHolderName: String,
            @SerializedName("acc_number")
            val accNumber: String,
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
            val bankAddress: String,
            @SerializedName("bank_name")
            val bankName: String,
            @SerializedName("branch_code")
            val branchCode: String,
            @SerializedName("cargo_position")
            val cargoPosition: CargoPosition,
            @SerializedName("city")
            val city: Any,
            @SerializedName("city2")
            val city2: String,
            @SerializedName("cod")
            val cod: Any,
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
            val customerSupportCity: List<String>,
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
            val email: String,
            @SerializedName("email_otp")
            val emailOtp: Int,
            @SerializedName("father_name")
            val fatherName: String,
            @SerializedName("file")
            val `file`: String,
            @SerializedName("first_time")
            val firstTime: Int,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("health_insurance_number")
            val healthInsuranceNumber: String,
            @SerializedName("_id")
            val id: String,
            @SerializedName("ifsc")
            val ifsc: String,
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

        data class CargoPosition(
            @SerializedName("coordinates")
            val coordinates: List<Int>,
            @SerializedName("type")
            val type: String
        )

        data class DeliveryPartner(
            @SerializedName("aadhar")
            val aadhar: String,
            @SerializedName("about")
            val about: String,
            @SerializedName("acc_holder_name")
            val accHolderName: String,
            @SerializedName("acc_number")
            val accNumber: String,
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
            val bankAddress: String,
            @SerializedName("bank_name")
            val bankName: String,
            @SerializedName("branch_code")
            val branchCode: String,
            @SerializedName("cargo_position")
            val cargoPosition: CargoPosition,
            @SerializedName("city")
            val city: Any,
            @SerializedName("city2")
            val city2: String,
            @SerializedName("cod")
            val cod: Any,
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
            val customerSupportCity: List<String>,
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
            val deviceToken: Any,
            @SerializedName("device_type")
            val deviceType: Any,
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
            val email: String,
            @SerializedName("email_otp")
            val emailOtp: Int,
            @SerializedName("father_name")
            val fatherName: String,
            @SerializedName("file")
            val `file`: String,
            @SerializedName("first_time")
            val firstTime: Int,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("health_insurance_number")
            val healthInsuranceNumber: String,
            @SerializedName("_id")
            val id: String,
            @SerializedName("ifsc")
            val ifsc: String,
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

        data class DeliveryPartnerPosition(
            @SerializedName("coordinates")
            val coordinates: List<Int>,
            @SerializedName("type")
            val type: String
        )

        data class PickupPartner(
            @SerializedName("aadhar")
            val aadhar: String,
            @SerializedName("about")
            val about: String,
            @SerializedName("acc_holder_name")
            val accHolderName: String,
            @SerializedName("acc_number")
            val accNumber: String,
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
            val bankAddress: String,
            @SerializedName("bank_name")
            val bankName: String,
            @SerializedName("branch_code")
            val branchCode: String,
            @SerializedName("cargo_position")
            val cargoPosition: CargoPosition,
            @SerializedName("city")
            val city: Any,
            @SerializedName("city2")
            val city2: String,
            @SerializedName("cod")
            val cod: Any,
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
            val customerSupportCity: List<String>,
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
            val email: String,
            @SerializedName("email_otp")
            val emailOtp: Int,
            @SerializedName("father_name")
            val fatherName: String,
            @SerializedName("file")
            val `file`: String,
            @SerializedName("first_time")
            val firstTime: Int,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("health_insurance_number")
            val healthInsuranceNumber: String,
            @SerializedName("_id")
            val id: String,
            @SerializedName("ifsc")
            val ifsc: String,
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

        data class Position(
            @SerializedName("coordinates")
            val coordinates: List<Int>,
            @SerializedName("type")
            val type: String
        )

        data class Product(
            @SerializedName("brand")
            val brand: String,
            @SerializedName("category")
            val category: String,
            @SerializedName("city")
            val city: String,
            @SerializedName("cuisine")
            val cuisine: String,
            @SerializedName("_id")
            val id: String,
            @SerializedName("price")
            val price: Int,
            @SerializedName("product")
            val product: Product,
            @SerializedName("productname")
            val productname: String,
            @SerializedName("quantity")
            val quantity: Int,
            @SerializedName("sub_category")
            val subCategory: String,
            @SerializedName("user")
            val user: String,
            @SerializedName("vendor")
            val vendor: String
        ) {
            data class Product(
                @SerializedName("active")
                val active: Int,
                @SerializedName("added_by")
                val addedBy: String,
                @SerializedName("attribute")
                val attribute: String,
                @SerializedName("backorders")
                val backorders: String,
                @SerializedName("batchno")
                val batchno: String,
                @SerializedName("brand")
                val brand: String,
                @SerializedName("category")
                val category: String,
                @SerializedName("cgst")
                val cgst: String,
                @SerializedName("city")
                val city: String,
                @SerializedName("combo_products")
                val comboProducts: Any,
                @SerializedName("commission")
                val commission: String,
                @SerializedName("consumable")
                val consumable: Int,
                @SerializedName("created_date")
                val createdDate: String,
                @SerializedName("cuisine")
                val cuisine: String,
                @SerializedName("deal")
                val deal: Int,
                @SerializedName("deleted")
                val deleted: Int,
                @SerializedName("desc")
                val desc: String,
                @SerializedName("discounted_price")
                val discountedPrice: String,
                @SerializedName("end_date")
                val endDate: String,
                @SerializedName("express")
                val express: Boolean,
                @SerializedName("featured")
                val featured: Int,
                @SerializedName("file")
                val `file`: List<File>,
                @SerializedName("height")
                val height: String,
                @SerializedName("_id")
                val id: String,
                @SerializedName("igst")
                val igst: String,
                @SerializedName("length")
                val length: String,
                @SerializedName("manage_stock")
                val manageStock: Int,
                @SerializedName("name")
                val name: String,
                @SerializedName("packaging_charge")
                val packagingCharge: String,
                @SerializedName("point")
                val point: Int,
                @SerializedName("point_exp_date")
                val pointExpDate: String,
                @SerializedName("price")
                val price: Int,
                @SerializedName("purchase_price")
                val purchasePrice: String,
                @SerializedName("selling_price")
                val sellingPrice: Any,
                @SerializedName("seo_description")
                val seoDescription: String,
                @SerializedName("seo_keywords")
                val seoKeywords: String,
                @SerializedName("seo_title")
                val seoTitle: String,
                @SerializedName("sgst")
                val sgst: String,
                @SerializedName("short_desc")
                val shortDesc: String,
                @SerializedName("sku")
                val sku: String,
                @SerializedName("slug")
                val slug: String,
                @SerializedName("slug_history")
                val slugHistory: List<String>,
                @SerializedName("start_date")
                val startDate: String,
                @SerializedName("stock_product")
                val stockProduct: String,
                @SerializedName("stock_qty")
                val stockQty: String,
                @SerializedName("sub_category")
                val subCategory: String,
                @SerializedName("tags")
                val tags: String,
                @SerializedName("taste")
                val taste: Int,
                @SerializedName("tax_status")
                val taxStatus: String,
                @SerializedName("threshold")
                val threshold: String,
                @SerializedName("top")
                val top: Int,
                @SerializedName("update_date")
                val updateDate: String,
                @SerializedName("__v")
                val v: Int,
                @SerializedName("vendor")
                val vendor: String,
                @SerializedName("weight")
                val weight: String,
                @SerializedName("width")
                val width: String
            ) {
                data class File(
                    @SerializedName("acl")
                    val acl: String,
                    @SerializedName("bucket")
                    val bucket: String,
                    @SerializedName("contentDisposition")
                    val contentDisposition: Any,
                    @SerializedName("contentType")
                    val contentType: String,
                    @SerializedName("encoding")
                    val encoding: String,
                    @SerializedName("etag")
                    val etag: String,
                    @SerializedName("fieldname")
                    val fieldname: String,
                    @SerializedName("key")
                    val key: String,
                    @SerializedName("location")
                    val location: String,
                    @SerializedName("metadata")
                    val metadata: Any,
                    @SerializedName("mimetype")
                    val mimetype: String,
                    @SerializedName("originalname")
                    val originalname: String,
                    @SerializedName("serverSideEncryption")
                    val serverSideEncryption: Any,
                    @SerializedName("size")
                    val size: Int,
                    @SerializedName("storageClass")
                    val storageClass: String
                )
            }
        }

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
            val email: String,
            @SerializedName("email_otp")
            val emailOtp: Int,
            @SerializedName("father_name")
            val fatherName: String,
            @SerializedName("file")
            val `file`: String,
            @SerializedName("first_time")
            val firstTime: Int,
            @SerializedName("full_name")
            val fullName: String,
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
            val otp: Any,
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

        data class Vendor(
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
            val city: String,
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
            val deliveryCity: List<String>,
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
            val email: String,
            @SerializedName("email_otp")
            val emailOtp: Int,
            @SerializedName("father_name")
            val fatherName: String,
            @SerializedName("file")
            val `file`: String,
            @SerializedName("first_time")
            val firstTime: Int,
            @SerializedName("full_name")
            val fullName: String,
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
            val profileImage: Any,
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

        data class VendorPosition(
            @SerializedName("coordinates")
            val coordinates: List<Int>,
            @SerializedName("type")
            val type: String
        )
    }
}