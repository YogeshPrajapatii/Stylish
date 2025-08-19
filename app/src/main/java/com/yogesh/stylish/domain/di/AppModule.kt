package com.yogesh.stylish.domain.di

import android.content.Context
import com.yogesh.stylish.data.local.UserPreferenceManager
import com.yogesh.stylish.data.remote.ProductApiService
import com.yogesh.stylish.data.repositoryimp.AuthRepositoryImp
import com.yogesh.stylish.data.repositoryimp.ProductRepositoryImpl
import com.yogesh.stylish.data.repositoryimp.UserPreferenceRepositoryImp
import com.yogesh.stylish.domain.repository.AuthRepository
import com.yogesh.stylish.domain.repository.ProductRepository
import com.yogesh.stylish.domain.repository.UserPreferenceRepository
import com.yogesh.stylish.domain.usecase.GetCategoriesUseCase
import com.yogesh.stylish.domain.usecase.GetProductByIdUseCase
import com.yogesh.stylish.domain.usecase.GetProductsUseCase
import com.yogesh.stylish.domain.usecase.LoginUseCase
import com.yogesh.stylish.domain.usecase.ReadOnboardingStatusUseCase
import com.yogesh.stylish.domain.usecase.SaveOnboardingStatusUseCase
import com.yogesh.stylish.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideGetProductByIdUseCase(repository: ProductRepository): GetProductByIdUseCase {
        return GetProductByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImp()
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(repository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesManager(@ApplicationContext context: Context): UserPreferenceManager {
        return UserPreferenceManager(context)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(manager: UserPreferenceManager): UserPreferenceRepository {
        return UserPreferenceRepositoryImp(manager)
    }

    @Provides
    @Singleton
    fun provideSaveOnboardingStatusUseCase(repository: UserPreferenceRepository): SaveOnboardingStatusUseCase {
        return SaveOnboardingStatusUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideReadOnboardingStatusUseCase(repository: UserPreferenceRepository):
            ReadOnboardingStatusUseCase {
        return ReadOnboardingStatusUseCase(repository)
    }

}