package me.taste2plate.app.customer.data.repo

import me.taste2plate.app.customer.data.api.CustomApi
import me.taste2plate.app.customer.domain.model.CategoryModel
import me.taste2plate.app.customer.domain.model.CityBrandModel
import me.taste2plate.app.customer.domain.model.CityListModel
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.StateListModel
import me.taste2plate.app.customer.domain.model.SubCategoryModel
import me.taste2plate.app.customer.domain.model.ZipListModel
import me.taste2plate.app.customer.domain.model.custom.AllPlanListModel
import me.taste2plate.app.customer.domain.repo.CustomRepo
import javax.inject.Inject

class CustomRepoImpl @Inject constructor(
    private val api: CustomApi
) : CustomRepo {
    override suspend fun settings(): SettingsModel {
        return api.settings()
    }

    override suspend fun home(
        taste: String
    ): HomeModel {
        return api.home(taste)
    }

    override suspend fun getCuisine(city: String): HomeModel {
        return api.getCuisine(city)
    }

    override suspend fun stateList(): StateListModel {
        return api.getStateList()
    }

    override suspend fun cityListByState(stateId: String): CityListModel {
        return api.fetchCityByState(stateId)
    }

    override suspend fun zipList(cityId: String): ZipListModel {
        return api.fetchZipList(cityId)
    }

    override suspend fun cityList(): CityBrandModel {
        return api.cityList()
    }

    override suspend fun brandList(): CityBrandModel {
        return api.brandList()
    }

    override suspend fun allCategories(): CategoryModel {
        return api.allCategories()
    }

    override suspend fun allSubCategories(categoryId: String): SubCategoryModel {
        return api.subCategories(categoryId)
    }

    override suspend fun getPlans(cityId: String): AllPlanListModel {
        return api.getPlans(cityId)
    }
}