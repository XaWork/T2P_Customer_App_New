package me.taste2plate.app.customer.domain.model.user


import com.google.gson.annotations.SerializedName

data class CartModel(
    @SerializedName("cartprice")
    val cartprice: String,
    @SerializedName("customer_point")
    val customerPoint: String,
    @SerializedName("express")
    val express: Boolean,
    @SerializedName("express_timeslot")
    val expressTimeslot: String,
    @SerializedName("final_price")
    val finalPrice: String,
    @SerializedName("gst")
    val gst: Gst,
    @SerializedName("gst_with_point")
    val gstWithPoint: GstWithPoint,
    @SerializedName("max_open_cod_order")
    val maxOpenCodOrder: String,
    @SerializedName("new_final_price")
    val newFinalPrice: NewFinalPrice,
    @SerializedName("normal_delivery_date")
    val normalDeliveryDate: String,
    @SerializedName("one_point_value")
    val onePointValue: String,
    @SerializedName("open_order_value")
    val openOrderValue: String,
    @SerializedName("plan_discount")
    val planDiscount: String,
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("shipping")
    val shipping: Shipping,
    @SerializedName("shipping_weight")
    val shippingWeight: Double,
    @SerializedName("status")
    val status: String,
    @SerializedName("total_cgst")
    val totalCgst: String,
    @SerializedName("total_igst")
    val totalIgst: String,
    @SerializedName("total_packing_price")
    val totalPackingPrice: String,
    @SerializedName("total_sgst")
    val totalSgst: String
) {

    data class Result(
        @SerializedName("brand")
        val brand: String,
        @SerializedName("category")
        val category: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("coupon")
        val coupon: String,
        @SerializedName("couponamount")
        val couponamount: String,
        @SerializedName("coupontype")
        val coupontype: String,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("cuisine")
        val cuisine: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("price")
        val price: String,
        @SerializedName("product")
        val product: Product,
        @SerializedName("productname")
        val productname: String,
        @SerializedName("quantity")
        val quantity: Int,
        @SerializedName("sub_category")
        val subCategory: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("user")
        val user: String,
        @SerializedName("__v")
        val v: String,
        @SerializedName("vendor")
        val vendor: Vendor
    ) {
        data class Product(
            @SerializedName("active")
            val active: String,
            @SerializedName("added_by")
            val addedBy: String,
            @SerializedName("attribute")
            val attribute: String,
            @SerializedName("backorders")
            val backorders: String,
            @SerializedName("batchno")
            val batchno: String,
            @SerializedName("brand")
            val brand: Brand,
            @SerializedName("category")
            val category: Category,
            @SerializedName("cgst")
            val cgst: String,
            @SerializedName("city")
            val city: City,
            @SerializedName("combo_products")
            val comboProducts: Any,
            @SerializedName("commission")
            val commission: String,
            @SerializedName("consumable")
            val consumable: String,
            @SerializedName("created_date")
            val createdDate: String,
            @SerializedName("cuisine")
            val cuisine: Cuisine,
            @SerializedName("deal")
            val deal: String,
            @SerializedName("deleted")
            val deleted: String,
            @SerializedName("desc")
            val desc: String,
            @SerializedName("discounted_price")
            val discountedPrice: String,
            @SerializedName("end_date")
            val endDate: String,
            @SerializedName("express")
            val express: Boolean,
            @SerializedName("featured")
            val featured: String,
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
            val manageStock: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("packaging_charge")
            val packagingCharge: String,
            @SerializedName("point")
            val point: String,
            @SerializedName("point_exp_date")
            val pointExpDate: String,
            @SerializedName("price")
            val price: String,
            @SerializedName("purchase_price")
            val purchasePrice: String,
            @SerializedName("selling_price")
            val sellingPrice: String,
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
            val subCategory: SubCategory,
            @SerializedName("tags")
            val tags: String,
            @SerializedName("taste")
            val taste: String,
            @SerializedName("tax_status")
            val taxStatus: String,
            @SerializedName("threshold")
            val threshold: String,
            @SerializedName("top")
            val top: String,
            @SerializedName("update_date")
            val updateDate: String,
            @SerializedName("__v")
            val v: String,
            @SerializedName("vendor")
            val vendor: Vendor,
            @SerializedName("weight")
            val weight: String,
            @SerializedName("width")
            val width: String
        ) {
            data class Brand(
                @SerializedName("_id")
                val id: String,
                @SerializedName("name")
                val name: String
            )

            data class Category(
                @SerializedName("_id")
                val id: String,
                @SerializedName("name")
                val name: String
            )

            data class City(
                @SerializedName("_id")
                val id: String,
                @SerializedName("name")
                val name: String
            )

            data class Cuisine(
                @SerializedName("_id")
                val id: String,
                @SerializedName("name")
                val name: String
            )

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
                val size: String,
                @SerializedName("storageClass")
                val storageClass: String
            )

            data class SubCategory(
                @SerializedName("_id")
                val id: String,
                @SerializedName("name")
                val name: String
            )

            data class Vendor(
                @SerializedName("full_name")
                val fullName: String,
                @SerializedName("_id")
                val id: String
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
            val active: String,
            @SerializedName("additional_cost1")
            val additionalCost1: String,
            @SerializedName("additional_cost2")
            val additionalCost2: String,
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
            val cod: String,
            @SerializedName("cod_order_cost")
            val codOrderCost: String,
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
            val deleted: String,
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
            val emailOtp: String,
            @SerializedName("father_name")
            val fatherName: String,
            @SerializedName("file")
            val `file`: String,
            @SerializedName("first_time")
            val firstTime: String,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("health_insurance_number")
            val healthInsuranceNumber: String,
            @SerializedName("_id")
            val id: String,
            @SerializedName("ifsc")
            val ifsc: Any,
            @SerializedName("login_active")
            val loginActive: String,
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
            val otp: String,
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
            val v: String,
            @SerializedName("vendor_position")
            val vendorPosition: VendorPosition,
            @SerializedName("zipcode")
            val zipcode: String
        ) {
            data class CargoPosition(
                @SerializedName("coordinates")
                val coordinates: List<String>,
                @SerializedName("type")
                val type: String
            )

            data class DeliveryPartnerPosition(
                @SerializedName("coordinates")
                val coordinates: List<String>,
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
                val point: String,
                @SerializedName("updated")
                val updated: Any
            )

            data class VendorPosition(
                @SerializedName("coordinates")
                val coordinates: List<String>,
                @SerializedName("type")
                val type: String
            )
        }
    }


}

data class Gst(
    @SerializedName("express")
    val express: Express,
    @SerializedName("normal")
    val normal: Normal
)
{
    data class Express(
        @SerializedName("total_cgst")
        val totalCgst: String,
        @SerializedName("total_igst")
        val totalIgst: String,
        @SerializedName("total_sgst")
        val totalSgst: String
    )

    data class Normal(
        @SerializedName("total_cgst")
        val totalCgst: String,
        @SerializedName("total_igst")
        val totalIgst: String,
        @SerializedName("total_sgst")
        val totalSgst: String
    )
}



data class GstWithPoint(
    @SerializedName("express")
    val express: Express,
    @SerializedName("normal")
    val normal: Normal
)
{
    data class Express(
        @SerializedName("total_cgst")
        val totalCgst: String,
        @SerializedName("total_igst")
        val totalIgst: String,
        @SerializedName("total_sgst")
        val totalSgst: String
    )

    data class Normal(
        @SerializedName("total_cgst")
        val totalCgst: String,
        @SerializedName("total_igst")
        val totalIgst: String,
        @SerializedName("total_sgst")
        val totalSgst: String
    )
}

data class Shipping(
    @SerializedName("express_shipping")
    val expressShipping: String,
    @SerializedName("normal_shipping")
    val normalShipping: String
)


data class NewFinalPrice(
    @SerializedName("express")
    val express: String,
    @SerializedName("normal")
    val normal: String,
    @SerializedName("with_wallet")
    val withWallet: WithWallet
)
{
    data class WithWallet(
        @SerializedName("express")
        val express: String,
        @SerializedName("normal")
        val normal: String
    )
}