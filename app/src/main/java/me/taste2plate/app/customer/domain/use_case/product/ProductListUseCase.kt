package me.taste2plate.app.customer.domain.use_case.product

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.product.ProductListModel
import me.taste2plate.app.customer.domain.repo.ProductRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductListUseCase @Inject constructor(
    private val repo: ProductRepo,
    private val userPref: UserPref
) {
    suspend fun execute(
        productBy: ProductBy,
        id: String
    ): Flow<Resource<ProductListModel>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val taste = userPref.getTaste()
                var response: ProductListModel? = null
                when (productBy) {
                    ProductBy.City -> {
                        response = repo.productByCity(id, taste)
                    }

                    ProductBy.Brand -> {
                        response = repo.productByBrand(id, taste)
                    }

                    ProductBy.Cuisine -> {
                        response = repo.productByCuisine(id, taste)
                    }

                    ProductBy.Category -> {
                        response = repo.productByCategory(id, taste)
                    }

                    ProductBy.Slider -> {
                Log.e("Product", "product id is $id and Product by is $productBy")
                        response = repo.productsBySlider(id, taste)
                    }

                    ProductBy.Search -> {
                        response = repo.productByQuery(id)
                    }
                }
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

enum class ProductBy {
    City,
    Brand,
    Cuisine,
    Slider,
    Search,
    Category
}