package me.taste2plate.app.customer.domain.use_case.analytics

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.domain.model.custom.IpAddressResponse
import me.taste2plate.app.customer.domain.repo.GeoIpRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GeoIpUseCase @Inject constructor(
    private val repo: GeoIpRepo,
) {
    suspend fun execute(): Flow<Resource<IpAddressResponse>>
    {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = repo.getIpAddress()
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