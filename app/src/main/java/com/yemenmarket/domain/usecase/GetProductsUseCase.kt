package com.yemenmarket.domain.usecase

import com.yemenmarket.domain.model.Result
import com.yemenmarket.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<com.yemenmarket.data.model.Product>> {
        return try {
            val result = repository.getProducts()
            if (result is Result.Success) {
                Result.Success(result.data)
            } else {
                Result.Failure(Exception("Failed to get products"))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
