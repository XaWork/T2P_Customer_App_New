package me.taste2plate.app.customer.domain.use_case.user.cart

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.repo.UserRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repo: UserRepo,
    private val userPref: UserPref
) {
    suspend fun execute(
        productId: String
    ): Flow<Resource<CommonResponse>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val defaultAddress = userPref.getDefaultAddress()
                val localAddress = userPref.getAddress()

              //  Log.e("Address", "City id is ${localAddress?.cityId}")

                if (defaultAddress != null || localAddress?.cityId != null) {
                    val response = repo.addToCart(
                        userId = userPref.getUser()!!.id,
                        pId = productId,
                        quantity = 1

                    )
                    emit(Resource.Success(response))
                }else{
                    emit(Resource.Error(message = "Please add/select your delivery address."))
                }
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