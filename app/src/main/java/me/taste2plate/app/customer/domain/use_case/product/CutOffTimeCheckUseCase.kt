package me.taste2plate.app.customer.domain.use_case.product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.domain.model.product.CutOffTimeCheckModel
import me.taste2plate.app.customer.domain.repo.ProductRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CutOffTimeCheckUseCase @Inject constructor(
    private val repo: ProductRepo,
) {
    suspend fun execute(
        startCity: String,
        endCity: String,
    ): Flow<Resource<CutOffTimeCheckModel>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = repo.checkCutOffTimeCheck(startCity, endCity)
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