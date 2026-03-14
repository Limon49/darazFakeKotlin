package com.example.darazfakenative.ui.theme

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.darazfakenative.data.preferences.ThemePreferences
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor(
    private val themePreferences: ThemePreferences
) {
    suspend fun isDarkTheme(): Boolean {
        return themePreferences.darkThemeFlow.first()
    }
    
    suspend fun setDarkTheme(isDark: Boolean) {
        themePreferences.setDarkTheme(isDark)
    }
}

@Composable
fun rememberThemeManager(
    themePreferences: ThemePreferences
): ThemeManager {
    return ThemeManager(themePreferences)
}
