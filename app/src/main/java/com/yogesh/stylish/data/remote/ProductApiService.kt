package com.yogesh.stylish.data.remote

import android.util.Log
import com.yogesh.stylish.data.remote.dto.ProductDto
import com.yogesh.stylish.data.remote.dto.ProductsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ProductApiService {


    private companion object {
        private const val TAG = "ProductApiService"
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(json)
        }
    }

    suspend fun getAllProducts(): ProductsResponseDto {
        val url =
            "https://dummyjson.com/products?limit=30&select=id,title,price,thumbnail,rating,discountPercentage,description"

        return try {
            val response = client.get(url).body<ProductsResponseDto>()
    
            Log.d("SUCCESS", "getAllProducts SUCCESS: Fetched ${response.products.size} products")
            response
        } catch (e: Exception) {
            Log.e("FAIL", "getAllProducts FAILED: Error fetching products", e)
            ProductsResponseDto(emptyList(), 0, 0, 0)
        }
    }

    suspend fun getAllCategories(): List<String> {
        val url = "https://dummyjson.com/products/categories"

        return try {
            val response: HttpResponse = client.get(url)
            val jsonString: String = response.bodyAsText()
            val categories = json.decodeFromString<List<String>>(jsonString)

            Log.d("SUCCESS", "getAllCategories SUCCESS: Decoded ${categories.size} categories")
            categories
        } catch (e: Exception) {
            Log.e("FAIL", "getAllCategories FAILED: Error decoding categories", e)
            emptyList()
        }
    }

    suspend fun getProductById(id: Int): ProductDto {
        val url = "https://dummyjson.com/products/$id"
        return client.get(url).body()
    }
}