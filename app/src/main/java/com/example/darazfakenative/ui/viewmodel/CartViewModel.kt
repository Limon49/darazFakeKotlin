package com.example.darazfakenative.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darazfakenative.data.model.CartItem
import com.example.darazfakenative.data.model.Order
import com.example.darazfakenative.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()
    
    init {
        viewModelScope.launch {
            cartRepository.cartItems.collect { cartItems ->
                _uiState.value = _uiState.value.copy(
                    cartItems = cartItems,
                    totalAmount = cartRepository.getCartTotal(),
                    itemCount = cartRepository.getCartItemCount()
                )
            }
        }
    }
    
    fun updateQuantity(productId: String, quantity: Int) {
        cartRepository.updateQuantity(productId, quantity)
    }
    
    fun removeFromCart(productId: String) {
        cartRepository.removeFromCart(productId)
    }
    
    fun toggleItemSelection(productId: String) {
        cartRepository.toggleItemSelection(productId)
    }
    
    fun selectAll() {
        cartRepository.selectAll()
    }
    
    fun deselectAll() {
        cartRepository.deselectAll()
    }
    
    fun checkout() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isCheckingOut = true, error = null)
            
            cartRepository.checkout().fold(
                onSuccess = { order ->
                    _uiState.value = _uiState.value.copy(
                        isCheckingOut = false,
                        checkoutSuccess = true,
                        order = order
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isCheckingOut = false,
                        error = exception.message
                    )
                }
            )
        }
    }
    
    fun clearCheckoutStatus() {
        _uiState.value = _uiState.value.copy(
            checkoutSuccess = false,
            order = null,
            error = null
        )
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val itemCount: Int = 0,
    val isCheckingOut: Boolean = false,
    val checkoutSuccess: Boolean = false,
    val order: Order? = null,
    val error: String? = null
)
