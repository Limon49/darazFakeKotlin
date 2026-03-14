package com.example.darazfakenative.data.repository

import com.example.darazfakenative.data.api.FakeData
import com.example.darazfakenative.data.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepository {
    
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: Flow<List<CartItem>> = _cartItems.asStateFlow()
    
    init {
        // Load some sample cart items
        _cartItems.value = listOf(
            CartItem(FakeData.products[0], 1),
            CartItem(FakeData.products[1], 2)
        )
    }
    
    fun addToCart(product: Product, quantity: Int = 1) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.product.id == product.id }
        
        if (existingItem != null) {
            val updatedItems = currentItems.map { 
                if (it.product.id == product.id) {
                    it.copy(quantity = it.quantity + quantity)
                } else {
                    it
                }
            }
            _cartItems.value = updatedItems
        } else {
            currentItems.add(CartItem(product, quantity))
            _cartItems.value = currentItems
        }
    }
    
    fun removeFromCart(productId: String) {
        val currentItems = _cartItems.value.toMutableList()
        currentItems.removeAll { it.product.id == productId }
        _cartItems.value = currentItems
    }
    
    fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
            return
        }
        
        val updatedItems = _cartItems.value.map { 
            if (it.product.id == productId) {
                it.copy(quantity = quantity)
            } else {
                it
            }
        }
        _cartItems.value = updatedItems
    }
    
    fun toggleItemSelection(productId: String) {
        val updatedItems = _cartItems.value.map { 
            if (it.product.id == productId) {
                it.copy(selected = !it.selected)
            } else {
                it
            }
        }
        _cartItems.value = updatedItems
    }
    
    fun selectAll() {
        val updatedItems = _cartItems.value.map { it.copy(selected = true) }
        _cartItems.value = updatedItems
    }
    
    fun deselectAll() {
        val updatedItems = _cartItems.value.map { it.copy(selected = false) }
        _cartItems.value = updatedItems
    }
    
    fun getSelectedItems(): List<CartItem> {
        return _cartItems.value.filter { it.selected }
    }
    
    fun getCartTotal(): Double {
        return _cartItems.value
            .filter { it.selected }
            .sumOf { it.product.price * it.quantity }
    }
    
    fun getCartItemCount(): Int {
        return _cartItems.value.sumOf { it.quantity }
    }
    
    suspend fun checkout(): Result<Order> {
        return try {
            delay(2000) // Simulate checkout process
            
            val selectedItems = getSelectedItems()
            if (selectedItems.isEmpty()) {
                return Result.failure(Exception("No items selected for checkout"))
            }
            
            val order = Order(
                id = "order_${System.currentTimeMillis()}",
                userId = FakeData.currentUser.id,
                items = selectedItems,
                totalAmount = getCartTotal(),
                status = OrderStatus.PENDING,
                orderDate = java.time.LocalDate.now().toString(),
                deliveryAddress = "123 Main St, City, Country"
            )
            
            // Clear cart after successful checkout
            _cartItems.value = emptyList()
            
            Result.success(order)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
