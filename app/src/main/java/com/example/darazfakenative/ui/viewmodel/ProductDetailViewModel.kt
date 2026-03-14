package com.example.darazfakenative.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darazfakenative.data.model.Product
import com.example.darazfakenative.data.model.Review
import com.example.darazfakenative.data.repository.CartRepository
import com.example.darazfakenative.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()
    
    fun loadProduct(productId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            productRepository.getProductById(productId).fold(
                onSuccess = { product ->
                    _uiState.value = _uiState.value.copy(
                        product = product,
                        isLoading = false,
                        error = null
                    )
                    loadProductReviews(productId)
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message
                    )
                }
            )
        }
    }
    
    private fun loadProductReviews(productId: String) {
        viewModelScope.launch {
            productRepository.getProductReviews(productId).fold(
                onSuccess = { reviews ->
                    _uiState.value = _uiState.value.copy(reviews = reviews)
                },
                onFailure = { exception ->
                    // Don't show error for reviews, just log it
                }
            )
        }
    }
    
    fun addToCart(product: Product, quantity: Int = 1) {
        cartRepository.addToCart(product, quantity)
        _uiState.value = _uiState.value.copy(addedToCart = true)
    }
    
    fun clearAddedToCartStatus() {
        _uiState.value = _uiState.value.copy(addedToCart = false)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class ProductDetailUiState(
    val product: Product? = null,
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val addedToCart: Boolean = false
)
