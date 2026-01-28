package com.yogesh.stylish.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.yogesh.stylish.data.local.dao.CartDao
import com.yogesh.stylish.data.local.dao.WishlistDao
import com.yogesh.stylish.data.local.data_store.user.UserPreferenceManager
import com.yogesh.stylish.data.local.database.StylishDatabase
import com.yogesh.stylish.data.remote.ProductApiService
import com.yogesh.stylish.data.repositoryimp.auth.AuthRepositoryImp
import com.yogesh.stylish.data.repositoryimp.cart.CartRepositoryImpl
import com.yogesh.stylish.data.repositoryimp.product.ProductRepositoryImpl
import com.yogesh.stylish.data.repositoryimp.userprefs.UserPreferenceRepositoryImp
import com.yogesh.stylish.data.repositoryimp.wishlist.WishlistRepositoryImpl
import com.yogesh.stylish.domain.repository.auth.AuthRepository
import com.yogesh.stylish.domain.repository.cart.CartRepository
import com.yogesh.stylish.domain.repository.product.ProductRepository
import com.yogesh.stylish.domain.repository.userprefs.UserPreferenceRepository
import com.yogesh.stylish.domain.repository.wishlist.WishlistRepository
import com.yogesh.stylish.domain.usecase.auth.LoginUseCase
import com.yogesh.stylish.domain.usecase.auth.LogoutUseCase
import com.yogesh.stylish.domain.usecase.auth.SignUpUseCase
import com.yogesh.stylish.domain.usecase.cart.AddToCartUseCase
import com.yogesh.stylish.domain.usecase.cart.ClearCartUseCase
import com.yogesh.stylish.domain.usecase.cart.GetCartItemsUseCase
import com.yogesh.stylish.domain.usecase.cart.RemoveFromCartUseCase
import com.yogesh.stylish.domain.usecase.cart.UpdateCartQuantityUseCase
import com.yogesh.stylish.domain.usecase.product.GetCategoriesUseCase
import com.yogesh.stylish.domain.usecase.product.GetProductByIdUseCase
import com.yogesh.stylish.domain.usecase.product.GetProductsUseCase
import com.yogesh.stylish.domain.usecase.userprefs.ReadOnboardingStatusUseCase
import com.yogesh.stylish.domain.usecase.userprefs.SaveOnboardingStatusUseCase
import com.yogesh.stylish.domain.usecase.wishlist.AddToWishlistUseCase
import com.yogesh.stylish.domain.usecase.wishlist.CheckWishlistStatusUseCase
import com.yogesh.stylish.domain.usecase.wishlist.ClearWishlistUseCase
import com.yogesh.stylish.domain.usecase.wishlist.GetWishlistProductsUseCase
import com.yogesh.stylish.domain.usecase.wishlist.RemoveFromWishlistUseCase
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
    fun provideLogoutUseCase(repository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideStylishDatabase(@ApplicationContext context: Context): StylishDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            StylishDatabase::class.java,
            "stylish_database.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideWishlistDao(database: StylishDatabase): WishlistDao {
        return database.wishlistDao()
    }

    @Provides
    @Singleton
    fun provideCartDao(database: StylishDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideWishlistRepository(wishlistDao: WishlistDao): WishlistRepository {
        return WishlistRepositoryImpl(wishlistDao)
    }

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository {
        return CartRepositoryImpl(cartDao)
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
    fun provideReadOnboardingStatusUseCase(repository: UserPreferenceRepository): ReadOnboardingStatusUseCase {
        return ReadOnboardingStatusUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImp(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAddToCartUseCase(repository: CartRepository): AddToCartUseCase {
        return AddToCartUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCartItemsUseCase(repository: CartRepository): GetCartItemsUseCase {
        return GetCartItemsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRemoveFromCartUseCase(repository: CartRepository): RemoveFromCartUseCase {
        return RemoveFromCartUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateCartQuantityUseCase(repository: CartRepository): UpdateCartQuantityUseCase {
        return UpdateCartQuantityUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideClearCartUseCase(repository: CartRepository): ClearCartUseCase {
        return ClearCartUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCheckWishlistStatusUseCase(repository: WishlistRepository): CheckWishlistStatusUseCase {
        return CheckWishlistStatusUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideClearWishlistUseCase(repository: WishlistRepository): ClearWishlistUseCase {
        return ClearWishlistUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetWishlistProductsUseCase(repository: WishlistRepository): GetWishlistProductsUseCase {
        return GetWishlistProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRemoveFromWishlistUseCase(repository: WishlistRepository): RemoveFromWishlistUseCase {
        return RemoveFromWishlistUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddToWishlistUseCase(repository: WishlistRepository): AddToWishlistUseCase {
        return AddToWishlistUseCase(repository)
    }
}