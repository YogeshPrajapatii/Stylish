package com.yogesh.stylish.presentation.feature.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yogesh.stylish.domain.usecase.product.GetProductByIdUseCase
import com.yogesh.stylish.domain.util.Result
import com.yogesh.stylish.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    savedStateHandle: SavedStateHandle 
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailState())
    val state = _state.asStateFlow()

    init {
        val args: Routes.ProductDetailScreen = savedStateHandle.toRoute()
        val productId = args.productId

        if (productId != -1) {
            getProductById(productId)
        } else {
            _state.update { it.copy(isLoading = false, error = "Invalid Product ID.") }
        }
    }

    private fun getProductById(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = getProductByIdUseCase(id)) {
                is Result.Failure -> {
                    _state.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
                is Result.Success -> {
                    _state.update {
                        it.copy(product = result.data, isLoading = false, error = null)
                    }
                }
                else -> {
                }
            }
        }
    }
}