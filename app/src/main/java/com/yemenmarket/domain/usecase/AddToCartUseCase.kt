package com.yemenmarket.domain.usecase

import com.yemenmarket.data.model.CartItem
import com.yemenmarket.domain.model.Result
import com.yemenmarket.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(item: CartItem): Result<Unit> {
        return try {
            val result = repository.addToCart(item)
            if (result.isSuccess) {
                Result.Success(Unit)
            } else {
                Result.Failure(Exception("Failed to add to cart"))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
