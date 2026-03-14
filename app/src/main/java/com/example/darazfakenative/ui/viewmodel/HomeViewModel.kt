package com.example.darazfakenative.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darazfakenative.data.model.Product
import com.example.darazfakenative.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeUiState> = combine(
        _products,
        _isLoading,
        _error,
        _searchQuery
    ) { products: List<Product>, isLoading: Boolean, error: String?, searchQuery: String ->
        HomeUiState(
            products = products,
            isLoading = isLoading,
            error = error,
            searchQuery = searchQuery
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )
    
    init {
        loadProducts()
    }
    
    private fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            productRepository.getProducts().fold(
                onSuccess = { products ->
                    _products.value = products
                    _isLoading.value = false
                    _error.value = null
                },
                onFailure = { exception ->
                    _isLoading.value = false
                    _error.value = exception.message
                }
            )
        }
    }
    
    fun searchProducts(query: String) {
        _searchQuery.value = query
        
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            if (query.isBlank()) {
                loadProducts()
                return@launch
            }
            
            productRepository.searchProducts(query).fold(
                onSuccess = { products ->
                    _products.value = products
                    _isLoading.value = false
                    _error.value = null
                },
                onFailure = { exception ->
                    _isLoading.value = false
                    _error.value = exception.message
                }
            )
        }
    }
    
    fun refresh() {
        loadProducts()
    }
    
    fun clearError() {
        _error.value = null
    }
}

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)
