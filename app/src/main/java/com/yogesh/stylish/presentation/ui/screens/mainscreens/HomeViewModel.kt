package com.yogesh.stylish.presentation.ui.screens.mainscreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.usecase.GetCategoriesUseCase
import com.yogesh.stylish.domain.usecase.GetProductsUseCase
import com.yogesh.stylish.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenState(

    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val error: String? = null

)

@HiltViewModel
class HomeViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase,
                                        private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        getProducts()
        getCategories()
    }

    private fun getProducts() {

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = getProductsUseCase()) {

                is Result.Success -> {

                    _state.value = _state.value.copy(

                        products = result.data, error = null, isLoading = false)

                }

                is Result.Failure -> {

                    _state.value = _state.value.copy(

                        isLoading = false, error = result.message)

                }

                else -> {}
            }
        }

    }

    private fun getCategories() {


        viewModelScope.launch {


            when (val result = getCategoriesUseCase()) {

                is Result.Success -> {

                    _state.value = _state.value.copy(

                        categories = result.data, error = null)

                }

                is Result.Failure -> {

                    _state.value = _state.value.copy(

                        isLoading = false, error = result.message)

                }

                else -> {}
            }
        }
    }


}