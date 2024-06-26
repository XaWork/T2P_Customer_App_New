package me.taste2plate.app.customer.domain.use_case.user.cart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.repo.UserRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val repo: UserRepo,
    private val userPref: UserPref
) {
    suspend fun execute(): Flow<Resource<CartModel>> {
        val defaultAddress = userPref.getDefaultAddress()
        val localAddress = userPref.getAddress()
        /*  val city = if (defaultAddress != null) null else localAddress?.cityId
          val zip = if (defaultAddress != null) null else localAddress?.pinCode*/
        val city = defaultAddress?.city?.id ?: localAddress?.cityId
        val zip = defaultAddress?.pincode ?: localAddress?.pinCode
        val userId = userPref.getUser()!!.id

        return flow {
            emit(Resource.Loading(true))
            try {
                val response = repo.getCart(userId, defaultAddress?.id ?: "", city, zip)
                /*val response: CartModel = if (defaultAddress == null)
                else
                    repo.getCartByAddress(userId, defaultAddress.id)*/
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
        }.catch { e ->
            e.printStackTrace()
            emit(
                Resource.Error(
                    message = when (e) {
                        is IOException -> ApiErrorMessages.errorIOException
                        is HttpException -> ApiErrorMessages.httpException
                        else -> ApiErrorMessages.eException
                    }
                )
            )
        }
    }
}