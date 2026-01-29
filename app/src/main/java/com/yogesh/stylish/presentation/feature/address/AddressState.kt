package com.yogesh.stylish.presentation.feature.address

import com.yogesh.stylish.data.local.entity.AddressEntity

data class AddressState(
    val isLoading: Boolean = false,
    val addresses: List<AddressEntity> = emptyList(),
    val error: String? = null,
    val isAddressAdded: Boolean = false
)