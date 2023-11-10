package me.taste2plate.app.customer.domain.model.product


import com.google.gson.annotations.SerializedName

data class ProductListModel(
    @SerializedName("count")
    val count: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("status")
    val status: String
) {
    data class Result(
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
        val consumable: Int,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("cuisine")
        val cuisine: Cuisine,
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
        val subCategory: SubCategory,
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
}