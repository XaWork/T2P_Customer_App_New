package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.CategoryModel
import me.taste2plate.app.customer.domain.model.CityBrandModel
import me.taste2plate.app.customer.domain.model.CityListModel
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.StateListModel
import me.taste2plate.app.customer.domain.model.SubCategoryModel
import me.taste2plate.app.customer.domain.model.ZipListModel
import me.taste2plate.app.customer.domain.model.custom.AllPlanListModel

interface CustomRepo {
    suspend fun settings(): SettingsModel
    suspend fun home(
        taste: String
    ): HomeModel

    suspend fun getCuisine(
        city: String
    ): HomeModel

    suspend fun stateList(): StateListModel
    suspend fun cityListByState(
        stateId: String
    ): CityListModel

    suspend fun zipList(
        cityId: String
    ): ZipListModel


    suspend fun cityList(): CityBrandModel

    suspend fun brandList(): CityBrandModel

    suspend fun allCategories(): CategoryModel

    suspend fun allSubCategories(
        categoryId: String
    ): SubCategoryModel

    suspend fun getPlans(
        cityId: String
    ): AllPlanListModel
}