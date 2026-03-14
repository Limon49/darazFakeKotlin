package com.example.darazfakenative.data.repository

import com.example.darazfakenative.data.api.FakeApiService
import com.example.darazfakenative.data.api.FakeData
import com.example.darazfakenative.data.model.*
import kotlinx.coroutines.delay

class ProductRepository(
    private val apiService: FakeApiService
) {
    
    suspend fun getProducts(): Result<List<Product>> {
        return try {
            delay(1000) // Simulate network delay
            Result.success(FakeData.products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getProductById(id: String): Result<Product> {
        return try {
            delay(500) // Simulate network delay
            val product = FakeData.products.find { it.id == id }
            if (product != null) {
                Result.success(product)
            } else {
                Result.failure(Exception("Product not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getProductsByCategory(category: String): Result<List<Product>> {
        return try {
            delay(800) // Simulate network delay
            val filteredProducts = FakeData.products.filter { it.category == category }
            Result.success(filteredProducts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun searchProducts(query: String): Result<List<Product>> {
        return try {
            delay(600) // Simulate network delay
            val searchResults = FakeData.products.filter { 
                it.name.contains(query, ignoreCase = true) || 
                it.description.contains(query, ignoreCase = true) ||
                it.brand.contains(query, ignoreCase = true)
            }
            Result.success(searchResults)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getProductReviews(productId: String): Result<List<Review>> {
        return try {
            delay(400) // Simulate network delay
            val reviews = FakeData.reviews.filter { it.productId == productId }
            Result.success(reviews)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
