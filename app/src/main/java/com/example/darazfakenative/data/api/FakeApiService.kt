package com.example.darazfakenative.data.api

import com.example.darazfakenative.data.model.*
import kotlinx.coroutines.delay
import retrofit2.Response
import retrofit2.http.*

interface FakeApiService {
    
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
    
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<Product>
    
    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): Response<List<Product>>
    
    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<List<Product>>
    
    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>
    
    @GET("products/{id}/reviews")
    suspend fun getProductReviews(@Path("id") productId: String): Response<List<Review>>
    
    @POST("orders")
    suspend fun createOrder(@Body order: Order): Response<Order>
    
    @GET("user/{id}/orders")
    suspend fun getUserOrders(@Path("id") userId: String): Response<List<Order>>
}
