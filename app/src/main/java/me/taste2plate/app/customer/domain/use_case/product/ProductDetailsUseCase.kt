package me.taste2plate.app.customer.domain.use_case.product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.product.ProductDetailsModel
import me.taste2plate.app.customer.domain.repo.ProductRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductDetailsUseCase @Inject constructor(
    private val userPref: UserPref,
    private val repo: ProductRepo,
) {
    suspend fun execute(
        id: String
    ): Flow<Resource<ProductDetailsModel>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val defaultAddress = userPref.getDefaultAddress()
                val localAddress = userPref.getAddress()
                val lat: String
                val lng: String
                val city: String

                if(defaultAddress != null){
                    lat = defaultAddress.position.coordinates[0].toString()
                    lng = defaultAddress.position.coordinates[0].toString()
                    city = defaultAddress.city.id
                }else{
                    lat = localAddress?.lat.toString()
                    lng = localAddress?.lng.toString()
                    city = localAddress?.cityId.toString()
                }
                val response = repo.productDetails(id, lat, lng, city)
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