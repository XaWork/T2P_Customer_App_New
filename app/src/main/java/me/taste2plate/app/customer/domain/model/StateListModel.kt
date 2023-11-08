package me.taste2plate.app.customer.domain.model


import com.google.gson.annotations.SerializedName

data class StateListModel(
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("status")
    val status: String
) {
    data class Result(
        @SerializedName("active")
        val active: Int,
        @SerializedName("city")
        val city: List<City>,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String
    ) {
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
    }
}