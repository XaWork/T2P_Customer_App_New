package me.taste2plate.app.customer.domain.use_case.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.domain.model.user.GetCityFromZipModel
import me.taste2plate.app.customer.domain.repo.CustomRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FetchCityUsingUseCase @Inject constructor(
    private val repo: CustomRepo,
) {
    suspend fun execute(
        zip: String
    ): Flow<Resource<GetCityFromZipModel>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = repo.fetchCityUsingZip(zip)
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