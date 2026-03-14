package com.example.darazfakenative

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.darazfakenative.data.preferences.ThemePreferences
import com.example.darazfakenative.navigation.DarazNavigation
import com.example.darazfakenative.ui.theme.DarazFakeNativeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var themePreferences: ThemePreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by themePreferences.darkThemeFlow.collectAsStateWithLifecycle(initialValue = false)
            
            DarazFakeNativeTheme(
                darkTheme = isDarkTheme,
                dynamicColor = false // Use our custom Daraz theme instead of dynamic colors
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DarazNavigation()
                }
            }
        }
    }
}