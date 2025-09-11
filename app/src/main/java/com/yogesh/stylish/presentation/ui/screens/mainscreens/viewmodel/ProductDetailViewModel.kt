package com.yogesh.stylish.presentation.ui.screens.mainscreens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.usecase.GetProductByIdUseCase
import com.yogesh.stylish.domain.util.Result
import com.yogesh.stylish.presentation.ui.screens.mainscreens.product_detail_screen.ProductDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(ProductDetailState())
    val state = _state.asStateFlow()

    fun getProductById(id: Int) {

        viewModelScope.launch {

            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = getProductByIdUseCase(id)) {
                is Result.Failure -> {
                    _state.update {
                        it.copy(product = null, isLoading = false, error = result.message)
                    }

                }

                Result.Ideal -> {
                    _state.update { it.copy(isLoading = false, error = null, product = null) }
                }

                Result.Loading -> {
                    _state.update { it.copy(isLoading = true, error = null) }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(product = result.data, isLoading = false, error = null)
                    }
                }
            }
        }
    }
}