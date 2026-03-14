package com.example.darazfakenative.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darazfakenative.data.model.Category
import com.example.darazfakenative.data.model.User
import com.example.darazfakenative.data.preferences.ThemePreferences
import com.example.darazfakenative.data.repository.CategoryRepository
import com.example.darazfakenative.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val themePreferences: ThemePreferences
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    
    init {
        loadUserData()
        loadCategories()
        observeTheme()
    }
    
    private fun observeTheme() {
        viewModelScope.launch {
            themePreferences.darkThemeFlow.collect { isDarkTheme ->
                _uiState.value = _uiState.value.copy(isDarkTheme = isDarkTheme)
            }
        }
    }
    
    private fun loadUserData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            userRepository.getCurrentUser().fold(
                onSuccess = { user ->
                    _uiState.value = _uiState.value.copy(
                        user = user,
                        isLoading = false,
                        error = null
                    )
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
    
    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getCategories().fold(
                onSuccess = { categories ->
                    _uiState.value = _uiState.value.copy(categories = categories)
                },
                onFailure = { exception ->
                    // Don't show error for categories in profile
                }
            )
        }
    }
    
    fun toggleTheme() {
        viewModelScope.launch {
            val newTheme = !_uiState.value.isDarkTheme
            themePreferences.setDarkTheme(newTheme)
        }
    }
    
    fun refresh() {
        loadUserData()
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class ProfileUiState(
    val user: User? = null,
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDarkTheme: Boolean = false
)
