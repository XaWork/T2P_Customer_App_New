package me.taste2plate.app.customer.domain.model.custom


import com.google.gson.annotations.SerializedName

data class GharKaKhanaCategoryModel(
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("status")
    val status: String
) {
    data class Result(
        @SerializedName("active")
        val active: Int,
        @SerializedName("additional_cost")
        val additionalCost: String,
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
        val parent: Any?,
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
}