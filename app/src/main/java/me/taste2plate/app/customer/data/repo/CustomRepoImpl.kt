package me.taste2plate.app.customer.data.repo

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.api.CustomApi
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.domain.model.CityListModel
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.StateListModel
import me.taste2plate.app.customer.domain.model.ZipListModel
import me.taste2plate.app.customer.domain.repo.CustomRepo
import retrofit2.HttpException
import java.io.IOException
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

    override suspend fun stateList(): StateListModel {
        return api.getStateList()
    }

    override suspend fun cityList(stateId: String): CityListModel {
        return api.fetchCityByState(stateId)
    }

    override suspend fun zipList(cityId: String): ZipListModel {
        return api.fetchZipList(cityId)
    }
}