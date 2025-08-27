package com.yogesh.stylish.data.remote

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

        return client.get(url).body()
    }

    suspend fun getAllCategories(): List<String> {
        val url = "https://dummyjson.com/products/categories"
        val response: HttpResponse = client.get(url)
        val jsonString: String = response.bodyAsText()

        return json.decodeFromString<List<String>>(jsonString)
    }

    suspend fun getProductById(id: Int): ProductDto {
        val url = "https://dummyjson.com/products/$id"
        return client.get(url).body()
    }
}