package me.taste2plate.app.customer.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.domain.model.CategoryModel
import me.taste2plate.app.customer.domain.model.CityBrandModel
import me.taste2plate.app.customer.domain.model.SubCategoryModel
import me.taste2plate.app.customer.domain.repo.CustomRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SubCategoryUseCase @Inject constructor(
    private val customRepo: CustomRepo
) {
    suspend fun execute(
        categoryId: String,
    ): Flow<Resource<SubCategoryModel>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = customRepo.allSubCategories(categoryId)
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