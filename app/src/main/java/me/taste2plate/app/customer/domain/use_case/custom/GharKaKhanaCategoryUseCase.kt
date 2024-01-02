package me.taste2plate.app.customer.domain.use_case.custom

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.custom.AllPlanListModel
import me.taste2plate.app.customer.domain.model.custom.GharKaKhanaCategoryModel
import me.taste2plate.app.customer.domain.repo.CustomRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GharKaKhanaCategoryUseCase @Inject constructor(
    private val repo: CustomRepo,
) {
    suspend fun execute(): Flow<Resource<GharKaKhanaCategoryModel>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = repo.gharKaKhanaCategory()
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