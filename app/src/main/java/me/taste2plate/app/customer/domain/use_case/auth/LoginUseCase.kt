package me.taste2plate.app.customer.domain.use_case.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.auth.LoginModel
import me.taste2plate.app.customer.domain.repo.CustomRepo
import me.taste2plate.app.customer.domain.repo.UserRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: UserRepo
) {
    suspend fun login(
        mobile: String,
        token: String,
        device: String
    ): Flow<Resource<LoginModel>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = repo.login(
                    mobile = mobile,
                    token = token,
                    device = device
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