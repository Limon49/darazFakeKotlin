package com.example.darazfakenative.di

import com.example.darazfakenative.data.api.FakeApiService
import com.example.darazfakenative.data.repository.CartRepository
import com.example.darazfakenative.data.repository.CategoryRepository
import com.example.darazfakenative.data.repository.ProductRepository
import com.example.darazfakenative.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/") // Assuming this is the base URL based on the name
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFakeApiService(retrofit: Retrofit): FakeApiService {
        return retrofit.create(FakeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: FakeApiService
    ): ProductRepository {
        return ProductRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideCartRepository(): CartRepository {
        return CartRepository()
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(): CategoryRepository {
        return CategoryRepository()
    }

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepository()
    }
}
