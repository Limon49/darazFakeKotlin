package com.example.darazfakenative.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object PerformanceUtils {
    private const val TAG = "Performance"
    
    fun logPerformance(operation: String, startTime: Long) {
        val duration = System.currentTimeMillis() - startTime
        Log.d(TAG, "$operation took ${duration}ms")
    }
    
    inline fun <T> measureTime(operation: String, block: () -> T): T {
        val startTime = System.currentTimeMillis()
        val result = block()
        logPerformance(operation, startTime)
        return result
    }
    
    fun runOnBackgroundThread(block: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            block()
        }
    }
}

@Composable
fun rememberPerformanceMonitor(operation: String) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val startTime = remember { System.currentTimeMillis() }
    
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    Log.d("Performance", "$operation: Composable created")
                }
                Lifecycle.Event.ON_DESTROY -> {
                    val duration = System.currentTimeMillis() - startTime
                    Log.d("Performance", "$operation: Composable destroyed after ${duration}ms")
                }
                else -> {}
            }
        }
        
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
