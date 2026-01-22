package com.yemenmarket.domain.repository

import com.yemenmarket.data.model.Product
import com.yemenmarket.domain.model.Result

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getProductById(id: String): Result<Product>
    suspend fun searchProducts(query: String): Result<List<Product>>
    suspend fun getProductsByCategory(category: String): Result<List<Product>>
}
