package com.yogesh.stylish.domain.di

import com.yogesh.stylish.data.remote.ProductApiService
import com.yogesh.stylish.data.repositoryimp.ProductRepositoryImpl
import com.yogesh.stylish.domain.repository.ProductRepository
import com.yogesh.stylish.domain.usecase.GetCategoriesUseCase
import com.yogesh.stylish.domain.usecase.GetProductByIdUseCase
import com.yogesh.stylish.domain.usecase.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module

@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideProductApiService(): ProductApiService {
        return ProductApiService()
    }

    @Provides
    @Singleton
    fun provideProductRepository(apiService: ProductApiService): ProductRepository {
        return ProductRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetProductsUseCase(repository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(repository: ProductRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetProductByIdUseCase(repository: ProductRepository): GetProductByIdUseCase {
        return GetProductByIdUseCase(repository)
    }
}