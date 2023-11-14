package me.taste2plate.app.customer.domain.use_case.product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.product.ProductDetailsModel
import me.taste2plate.app.customer.domain.model.product.ProductListModel
import me.taste2plate.app.customer.domain.repo.ProductRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductDetailsUseCase @Inject constructor(
    private val repo: ProductRepo,
) {
    suspend fun execute(
        id: String
    ): Flow<Resource<ProductDetailsModel>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = repo.productDetails(id)
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