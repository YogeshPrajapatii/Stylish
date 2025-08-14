package com.yogesh.stylish.data.remote

import com.yogesh.stylish.data.remote.dto.ProductsResponseDto
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ProductApiService {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true 
            })
        }
    }
    
    suspend fun getAllProducts(): ProductsResponseDto {
        val url = "https://dummyjson.com/products?limit=30&select=id,title,price,thumbnail,rating,discountPercentage,description"
       
        return client.get(url).body() 
    }
    
    suspend fun getAllCategories(): List<String> {
        val url = "https://dummyjson.com/products/categories"
        return client.get(url).body() 
    }
}