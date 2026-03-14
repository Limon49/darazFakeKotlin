package com.example.darazfakenative.data.repository

import com.example.darazfakenative.data.api.FakeData
import com.example.darazfakenative.data.model.*
import kotlinx.coroutines.delay

class CategoryRepository {
    
    suspend fun getCategories(): Result<List<Category>> {
        return try {
            delay(500) // Simulate network delay
            Result.success(FakeData.categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class UserRepository {
    
    suspend fun getCurrentUser(): Result<User> {
        return try {
            delay(300) // Simulate network delay
            Result.success(FakeData.currentUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUserOrders(userId: String): Result<List<Order>> {
        return try {
            delay(800) // Simulate network delay
            // Return empty list for now, can be extended with fake orders
            Result.success(emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
