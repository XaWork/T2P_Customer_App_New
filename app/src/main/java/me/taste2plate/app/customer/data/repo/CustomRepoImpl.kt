package me.taste2plate.app.customer.data.repo

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.api.CustomApi
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.repo.CustomRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CustomRepoImpl @Inject constructor(
    private val api: CustomApi
) : CustomRepo {
    override suspend fun settings(): SettingsModel {
        Log.e("CustomRepo", "Settings api called")
        return api.settings()
    }
    override suspend fun setting(): Flow<Resource<SettingsModel>> {

        return flow {
            emit(Resource.Loading(true))
            try {
                val response = api.settings()
                emit(Resource.Success(response))
            } catch (io: IOException) {
                io.printStackTrace()
                emit(Resource.Error(message = ApiErrorMessages.errorIOException))
            } catch (http: HttpException) {
                http.printStackTrace()
                emit(Resource.Error(message = ApiErrorMessages.httpException))
            } catch (http: Exception) {
                http.printStackTrace()
                emit(Resource.Error(message = ApiErrorMessages.eException))
            }
        }
    }
}