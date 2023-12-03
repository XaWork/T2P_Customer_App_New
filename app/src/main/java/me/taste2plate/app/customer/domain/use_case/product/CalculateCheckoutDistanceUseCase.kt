package me.taste2plate.app.customer.domain.use_case.product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.product.CalculateCheckoutDistanceModel
import me.taste2plate.app.customer.domain.model.product.CutOffTimeCheckModel
import me.taste2plate.app.customer.domain.repo.ProductRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CalculateCheckoutDistanceUseCase @Inject constructor(
    private val repo: ProductRepo,
    private val userPref: UserPref
) {
    suspend fun execute(
        productId: String,
    ): Flow<Resource<CalculateCheckoutDistanceModel>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = repo.calculateCheckoutDistance(
                    addressId = userPref.getDefaultAddress()!!.id,
                    productId = productId
                )
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