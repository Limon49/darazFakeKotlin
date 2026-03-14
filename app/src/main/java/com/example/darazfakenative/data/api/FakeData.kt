package com.example.darazfakenative.data.api

import com.example.darazfakenative.data.model.*

object FakeData {
    
    val categories = listOf(
        Category("1", "Electronics", "https://picsum.photos/seed/electronics/200/200", 1250),
        Category("2", "Fashion", "https://picsum.photos/seed/fashion/200/200", 3420),
        Category("3", "Home & Living", "https://picsum.photos/seed/home/200/200", 890),
        Category("4", "Sports", "https://picsum.photos/seed/sports/200/200", 567),
        Category("5", "Beauty", "https://picsum.photos/seed/beauty/200/200", 1234),
        Category("6", "Books", "https://picsum.photos/seed/books/200/200", 456)
    )
    
    val products = listOf(
        Product(
            id = "1",
            name = "Wireless Bluetooth Headphones",
            description = "Premium noise-cancelling wireless headphones with superior sound quality and 30-hour battery life.",
            price = 89.99,
            originalPrice = 149.99,
            discount = 40,
            rating = 4.5f,
            reviewCount = 2341,
            imageUrl = "https://picsum.photos/seed/headphones/400/400",
            category = "Electronics",
            brand = "SoundTech",
            inStock = true,
            freeShipping = true,
            specifications = mapOf(
                "Battery Life" to "30 hours",
                "Connectivity" to "Bluetooth 5.0",
                "Weight" to "250g"
            )
        ),
        Product(
            id = "2",
            name = "Smart Watch Pro",
            description = "Advanced fitness tracking smartwatch with heart rate monitor and GPS.",
            price = 199.99,
            originalPrice = 299.99,
            discount = 33,
            rating = 4.3f,
            reviewCount = 1567,
            imageUrl = "https://picsum.photos/seed/smartwatch/400/400",
            category = "Electronics",
            brand = "TechWatch",
            inStock = true,
            freeShipping = true,
            specifications = mapOf(
                "Display" to "1.4\" AMOLED",
                "Battery Life" to "7 days",
                "Water Resistance" to "5ATM"
            )
        ),
        Product(
            id = "3",
            name = "Designer Leather Jacket",
            description = "Genuine leather jacket with modern styling and premium craftsmanship.",
            price = 249.99,
            originalPrice = 399.99,
            discount = 38,
            rating = 4.7f,
            reviewCount = 892,
            imageUrl = "https://picsum.photos/seed/jacket/400/400",
            category = "Fashion",
            brand = "LuxStyle",
            inStock = true,
            freeShipping = false,
            specifications = mapOf(
                "Material" to "Genuine Leather",
                "Sizes" to "S, M, L, XL",
                "Care" to "Dry Clean Only"
            )
        ),
        Product(
            id = "4",
            name = "Yoga Mat Premium",
            description = "Extra thick non-slip yoga mat with alignment markers.",
            price = 29.99,
            originalPrice = 49.99,
            discount = 40,
            rating = 4.6f,
            reviewCount = 3421,
            imageUrl = "https://picsum.photos/seed/yogamat/400/400",
            category = "Sports",
            brand = "FitGear",
            inStock = true,
            freeShipping = true,
            specifications = mapOf(
                "Thickness" to "6mm",
                "Material" to "TPE",
                "Dimensions" to "183cm x 61cm"
            )
        ),
        Product(
            id = "5",
            name = "Coffee Maker Deluxe",
            description = "Programmable coffee maker with thermal carafe and auto-brew.",
            price = 79.99,
            originalPrice = 119.99,
            discount = 33,
            rating = 4.4f,
            reviewCount = 1876,
            imageUrl = "https://picsum.photos/seed/coffee/400/400",
            category = "Home & Living",
            brand = "BrewMaster",
            inStock = false,
            freeShipping = true,
            specifications = mapOf(
                "Capacity" to "12 cups",
                "Power" to "1000W",
                "Features" to "Programmable, Auto-brew"
            )
        ),
        Product(
            id = "6",
            name = "Skincare Set Premium",
            description = "Complete 5-piece skincare set with natural ingredients.",
            price = 59.99,
            originalPrice = 89.99,
            discount = 33,
            rating = 4.8f,
            reviewCount = 2934,
            imageUrl = "https://picsum.photos/seed/skincare/400/400",
            category = "Beauty",
            brand = "GlowUp",
            inStock = true,
            freeShipping = true,
            specifications = mapOf(
                "Pieces" to "5",
                "Skin Type" to "All Types",
                "Ingredients" to "Natural, Organic"
            )
        ),
        Product(
            id = "7",
            name = "Wireless Gaming Mouse",
            description = "High-precision gaming mouse with RGB lighting and programmable buttons.",
            price = 49.99,
            originalPrice = 79.99,
            discount = 38,
            rating = 4.5f,
            reviewCount = 1234,
            imageUrl = "https://picsum.photos/seed/mouse/400/400",
            category = "Electronics",
            brand = "GamePro",
            inStock = true,
            freeShipping = true,
            specifications = mapOf(
                "DPI" to "16000",
                "Buttons" to "7 Programmable",
                "Battery" to "60 hours"
            )
        ),
        Product(
            id = "8",
            name = "Running Shoes Pro",
            description = "Professional running shoes with advanced cushioning technology.",
            price = 89.99,
            originalPrice = 139.99,
            discount = 36,
            rating = 4.6f,
            reviewCount = 3456,
            imageUrl = "https://picsum.photos/seed/shoes/400/400",
            category = "Sports",
            brand = "SpeedRun",
            inStock = true,
            freeShipping = true,
            specifications = mapOf(
                "Weight" to "280g",
                "Drop" to "8mm",
                "Upper" to "Mesh"
            )
        )
    )
    
    val reviews = listOf(
        Review(
            id = "1",
            productId = "1",
            userName = "John Doe",
            rating = 5.0f,
            comment = "Amazing sound quality! The noise cancellation is incredible.",
            date = "2024-01-15",
            helpfulCount = 234
        ),
        Review(
            id = "2",
            productId = "1",
            userName = "Jane Smith",
            rating = 4.0f,
            comment = "Great headphones, but a bit heavy for long use.",
            date = "2024-01-10",
            helpfulCount = 89
        ),
        Review(
            id = "3",
            productId = "2",
            userName = "Mike Johnson",
            rating = 4.5f,
            comment = "Perfect for tracking my workouts. Battery life is excellent!",
            date = "2024-01-12",
            helpfulCount = 156
        )
    )
    
    val currentUser = User(
        id = "user1",
        name = "Alex User",
        email = "alex@example.com",
        phone = "+1234567890",
        avatar = "https://picsum.photos/seed/avatar/100/100"
    )
}
