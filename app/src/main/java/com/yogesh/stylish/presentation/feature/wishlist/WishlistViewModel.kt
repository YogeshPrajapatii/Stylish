package com.yogesh.stylish.presentation.feature.wishlist


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.usecase.wishlist.AddToWishlistUseCase
import com.yogesh.stylish.domain.usecase.wishlist.CheckWishlistStatusUseCase
import com.yogesh.stylish.domain.usecase.wishlist.ClearWishlistUseCase
import com.yogesh.stylish.domain.usecase.wishlist.GetWishlistProductsUseCase
import com.yogesh.stylish.domain.usecase.wishlist.RemoveFromWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.yogesh.stylish.domain.model.Product
        

@HiltViewModel
class WishlistViewModel @Inject constructor(private val getWishlistProductsUseCase: GetWishlistProductsUseCase,
                                            private val addToWishlistUseCase: AddToWishlistUseCase,
                                            private val removeFromWishlistUseCase: RemoveFromWishlistUseCase,
                                            private val checkWishlistStatusUseCase: CheckWishlistStatusUseCase,
                                            private val clearWishlistUseCase: ClearWishlistUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(WishlistState())

    val state: StateFlow<WishlistState> = _state.asStateFlow()


    init {
        loadWishlist()
    }

    private fun loadWishlist() {
        viewModelScope.launch {

            getWishlistProductsUseCase().onStart {
                _state.update { it.copy(isLoading = true, error = null) }
            }.catch { exception ->
                _state.update {
                    it.copy(isLoading = false,
                        error = exception.localizedMessage ?: "Failed to load wishlist")
                }
            }.collect { productList ->
                _state.update {
                    it.copy(isLoading = false, products = productList, error = null)
                }
            }
        }
    }

    fun addProductToWishlist(product: Product) {
        viewModelScope.launch {
            try {
                addToWishlistUseCase(product)
            } catch (e: Exception) {
                _state.update { it.copy(error = e.localizedMessage ?: "Failed to add item") }
            }
        }
    }

    fun removeFromWishlist(productId: Int) {
        viewModelScope.launch {
            try {
                removeFromWishlistUseCase(productId)
            } catch (e: Exception) {
                _state.update { it.copy(error = e.localizedMessage ?: "Failed to remove item") }
            }
        }
    }

    
    suspend fun checkIsProductInWishlist(productId: Int): Boolean {
        return try {
            checkWishlistStatusUseCase(productId)
        } catch (e: Exception) {
            _state.update { it.copy(error = e.localizedMessage ?: "Failed to check status") }
            false 
        }
    }


  
    fun clearAllWishlist() {
        viewModelScope.launch {
            try {
                clearWishlistUseCase()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.localizedMessage ?: "Failed to clear wishlist") }
            }
        }
    }


}