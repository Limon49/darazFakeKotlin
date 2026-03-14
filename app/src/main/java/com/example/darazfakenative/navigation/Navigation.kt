package com.example.darazfakenative.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.darazfakenative.ui.screens.*

@Composable
fun DarazNavigation(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = remember(currentRoute) { 
        currentRoute in listOf(BottomTab.Home.route, BottomTab.Profile.route) 
    }
    
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    navController = navController,
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    },
                    onCartClick = {
                        navController.navigate(Screen.Cart.route)
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomTab.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomTab.Home.route) {
                HomeScreen(
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    },
                    onCartClick = {
                        navController.navigate(Screen.Cart.route)
                    },
                    onProfileClick = {
                        // Already on home, no need to navigate
                    }
                )
            }
            
            composable(BottomTab.Profile.route) {
                ProfileScreen(
                    onBackClick = {
                        navController.navigate(BottomTab.Home.route) {
                            popUpTo(BottomTab.Home.route) {
                                inclusive = true
                            }
                        }
                    },
                    onCategoryClick = { categoryName ->
                        navController.navigate(BottomTab.Home.route) {
                            popUpTo(BottomTab.Home.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            
            composable(Screen.ProductDetail.route) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
                ProductDetailScreen(
                    productId = productId,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCartClick = {
                        navController.navigate(Screen.Cart.route)
                    }
                )
            }
            
            composable(Screen.Cart.route) {
                CartScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCheckout = {
                        navController.popBackStack()
                    },
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
    onProductClick: (String) -> Unit,
    onCartClick: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            BottomTab.values().forEach { tab ->
                val isSelected = currentRoute == tab.route
                val selectedColor = MaterialTheme.colorScheme.primary
                val unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            tint = if (isSelected) selectedColor else unselectedColor
                        )
                    },
                    label = {
                        Text(
                            text = tab.title,
                            color = if (isSelected) selectedColor else unselectedColor,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    selected = isSelected,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = {
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

enum class BottomTab(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    Home("home", "Home", Icons.Default.Home),
    Profile("profile", "Profile", Icons.Default.Person)
}

sealed class Screen(val route: String) {
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Cart : Screen("cart")
}
