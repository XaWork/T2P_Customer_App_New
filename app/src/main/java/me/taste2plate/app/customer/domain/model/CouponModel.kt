package me.taste2plate.app.customer.domain.model


import com.google.gson.annotations.SerializedName

data class CouponModel(
    @SerializedName("best_seller")
    val bestSeller: List<BestSeller>,
    @SerializedName("combo")
    val combo: List<Any>,
    @SerializedName("coupon")
    val coupon: List<Coupon>,
    @SerializedName("product_deal")
    val productDeal: List<ProductDeal>,
    @SerializedName("status")
    val status: String
) {
    data class BestSeller(
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
        @SerializedName("rating")
        val rating: String,
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
            @SerializedName("contentEncoding")
            val contentEncoding: Any,
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
            val storageClass: String,
            @SerializedName("versionId")
            val versionId: Any
        )
    }

    data class Coupon(
        @SerializedName("active")
        val active: Int,
        @SerializedName("amount")
        val amount: String,
        @SerializedName("brand")
        val brand: Any,
        @SerializedName("category")
        val category: List<String>,
        @SerializedName("coupon")
        val coupon: String,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("customer")
        val customer: Any,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("desc")
        val desc: String,
        @SerializedName("exclusive")
        val exclusive: String,
        @SerializedName("exp_date")
        val expDate: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("max_usage_limit")
        val maxUsageLimit: Int,
        @SerializedName("max_usage_limit_user")
        val maxUsageLimitUser: Int,
        @SerializedName("maximum_amount")
        val maximumAmount: String,
        @SerializedName("minimum_amount")
        val minimumAmount: String,
        @SerializedName("product")
        val product: Any,
        @SerializedName("start_date")
        val startDate: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class ProductDeal(
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
        @SerializedName("rating")
        val rating: String,
        @SerializedName("selling_price")
        val sellingPrice: Int,
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