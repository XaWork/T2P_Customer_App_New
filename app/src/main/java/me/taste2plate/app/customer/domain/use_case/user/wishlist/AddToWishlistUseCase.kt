package me.taste2plate.app.customer.domain.use_case.user.wishlist

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

class AddToWishlistUseCase @Inject constructor(
    private val repo: UserRepo,
    private val userPref: UserPref
) {
    suspend fun execute(
        productId: String
    ): Flow<Resource<CommonResponse>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = repo.addToWishlist(
                    userId = userPref.getUser().id,
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