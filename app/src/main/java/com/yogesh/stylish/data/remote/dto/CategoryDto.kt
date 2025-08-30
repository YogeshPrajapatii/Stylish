package com.yogesh.stylish.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val slug: String,
    val name: String,
    val url: String 
)