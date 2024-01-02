package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.CategoryModel
import me.taste2plate.app.customer.domain.model.CityBrandModel
import me.taste2plate.app.customer.domain.model.CityListModel
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.StateListModel
import me.taste2plate.app.customer.domain.model.SubCategoryModel
import me.taste2plate.app.customer.domain.model.ZipListModel
import me.taste2plate.app.customer.domain.model.custom.AllPlanListModel
import me.taste2plate.app.customer.domain.model.custom.GharKaKhanaCategoryModel
import me.taste2plate.app.customer.domain.model.custom.GharKaKhanaSubCategoryModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CustomApi {
    @POST("settings")
    suspend fun settings(): SettingsModel

    @GET("home")
    suspend fun home(
        @Query("taste") taste: String
    ): HomeModel

    @GET("home")
    suspend fun getCuisine(
        @Query("city") city: String
    ): HomeModel

    @GET("state-list")
    suspend fun getStateList(): StateListModel


    @GET("city-list-by-state")
    suspend fun fetchCityByState(@Query("state") stateId: String): CityListModel


    @GET("zipcode-by-city")
    suspend fun fetchZipList(@Query("city") cityId: String): ZipListModel


    @GET("all-cities")
    suspend fun cityList(
    ): CityBrandModel

    @GET("all-brands")
    suspend fun brandList(
    ): CityBrandModel

    @GET("all-parent-categories")
    suspend fun allCategories(): CategoryModel

    @GET("all-sub-categories")
    suspend fun subCategories(@Query("parent") parentId: String): SubCategoryModel


    @GET("all-plan-list")
    suspend fun getPlans(@Query("city") cityId: String): AllPlanListModel

    @POST("gharkakhana-category")
    suspend fun gharKaKhanaCategory(): GharKaKhanaCategoryModel

    @POST("gharkakhana-subcategory")
    suspend fun gharKaKhanaSubCategory(): GharKaKhanaSubCategoryModel

}