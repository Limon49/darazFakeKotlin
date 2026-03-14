package com.example.darazfakenative.data.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val originalPrice: Double,
    val discount: Int,
    val rating: Float,
    val reviewCount: Int,
    val imageUrl: String,
    val category: String,
    val brand: String,
    val inStock: Boolean,
    val freeShipping: Boolean,
    val specifications: Map<String, String> = emptyMap()
)

data class Category(
    val id: String,
    val name: String,
    val imageUrl: String,
    val productCount: Int
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val avatar: String = ""
)

data class CartItem(
    val product: Product,
    val quantity: Int,
    val selected: Boolean = true
)

data class Review(
    val id: String,
    val productId: String,
    val userName: String,
    val rating: Float,
    val comment: String,
    val date: String,
    val helpfulCount: Int = 0
)

data class Order(
    val id: String,
    val userId: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val status: OrderStatus,
    val orderDate: String,
    val deliveryAddress: String
)

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
