package me.taste2plate.app.customer.domain.model


import com.google.gson.annotations.SerializedName

data class HomeModel(
    @SerializedName("best_seller")
    val bestSeller: List<Any>,
    @SerializedName("category")
    val category: List<Category>,
    @SerializedName("city")
    val city: List<City>,
    @SerializedName("combo")
    val combo: List<Any>,
    @SerializedName("cuisine")
    val cuisine: List<Cuisine>,
    @SerializedName("featured")
    val featured: List<Featured>,
    @SerializedName("hidden_gems")
    val hiddenGems: List<Any>,
    @SerializedName("message")
    val message: String,
    @SerializedName("most_orderd_item")
    val mostOrderdItem: List<MostOrderdItem>,
    @SerializedName("product_deal")
    val productDeal: List<ProductDeal>,
    @SerializedName("service_city")
    val serviceCity: List<ServiceCity>,
    @SerializedName("slider")
    val slider: List<Slider>,
    @SerializedName("special")
    val special: List<Special>,
    @SerializedName("status")
    val status: String,
    @SerializedName("top_brands")
    val topBrands: List<TopBrand>,
    @SerializedName("top_most_ordered_products")
    val topMostOrderedProducts: List<TopMostOrderedProduct>
) {
    data class Category(
        @SerializedName("active")
        val active: Int,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("description_after_content")
        val descriptionAfterContent: String,
        @SerializedName("file")
        val `file`: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("parent")
        val parent: Any,
        @SerializedName("seo_description")
        val seoDescription: String,
        @SerializedName("seo_keywords")
        val seoKeywords: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class City(
        @SerializedName("active")
        val active: Int,
        @SerializedName("cod")
        val cod: Boolean,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("description_after_content")
        val descriptionAfterContent: String,
        @SerializedName("file")
        val `file`: String,
        @SerializedName("file2")
        val file2: String,
        @SerializedName("footer")
        val footer: Int,
        @SerializedName("footer_content")
        val footerContent: String,
        @SerializedName("heading")
        val heading: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("ps")
        val ps: String,
        @SerializedName("seo_description")
        val seoDescription: String,
        @SerializedName("seo_keywords")
        val seoKeywords: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        @SerializedName("state")
        val state: String,
        @SerializedName("sub_heading")
        val subHeading: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class Cuisine(
        @SerializedName("active")
        val active: Int,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("description_after_content")
        val descriptionAfterContent: String,
        @SerializedName("file")
        val `file`: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("seo_description")
        val seoDescription: String,
        @SerializedName("seo_keywords")
        val seoKeywords: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class Featured(
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
        val price: String,
        @SerializedName("purchase_price")
        val purchasePrice: String,
        @SerializedName("selling_price")
        val sellingPrice: String?,
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
        val vendor: String,
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
    }

    data class MostOrderdItem(
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
        val price: String,
        @SerializedName("purchase_price")
        val purchasePrice: String,
        @SerializedName("selling_price")
        val sellingPrice: String?,
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
        val vendor: String,
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
    }

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
            val size: Int,
            @SerializedName("storageClass")
            val storageClass: String
        )

        data class SubCategory(
            @SerializedName("_id")
            val id: String,
            @SerializedName("name")
            val name: String
        )
    }

    data class ServiceCity(
        @SerializedName("active")
        val active: Int,
        @SerializedName("cod")
        val cod: Boolean,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("description_after_content")
        val descriptionAfterContent: String,
        @SerializedName("file")
        val `file`: String,
        @SerializedName("file2")
        val file2: String,
        @SerializedName("footer")
        val footer: Int,
        @SerializedName("footer_content")
        val footerContent: String,
        @SerializedName("heading")
        val heading: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("ps")
        val ps: String,
        @SerializedName("seo_description")
        val seoDescription: String,
        @SerializedName("seo_keywords")
        val seoKeywords: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        @SerializedName("state")
        val state: String,
        @SerializedName("sub_heading")
        val subHeading: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class Slider(
        @SerializedName("active")
        val active: Int,
        @SerializedName("city")
        val city: City,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("file")
        val `file`: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    ) {
        data class City(
            @SerializedName("_id")
            val id: String,
            @SerializedName("name")
            val name: String
        )
    }

    data class Special(
        @SerializedName("active")
        val active: Int,
        @SerializedName("amount")
        val amount: String,
        @SerializedName("brand")
        val brand: List<String>,
        @SerializedName("category")
        val category: List<String>,
        @SerializedName("coupon")
        val coupon: String,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("customer")
        val customer: List<String>,
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
        val product: List<String>,
        @SerializedName("start_date")
        val startDate: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class TopBrand(
        @SerializedName("active")
        val active: Int,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("desc")
        val desc: String,
        @SerializedName("file")
        val `file`: String,
        @SerializedName("gem")
        val gem: Int,
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("seo_description")
        val seoDescription: String,
        @SerializedName("seo_keywords")
        val seoKeywords: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        @SerializedName("short_desc")
        val shortDesc: String,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        @SerializedName("top")
        val top: Int,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class TopMostOrderedProduct(
        @SerializedName("active")
        val active: Int,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("_id")
        val id: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("products")
        val products: List<Product>,
        @SerializedName("slider_name")
        val sliderName: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("__v")
        val v: Int
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
    }
}