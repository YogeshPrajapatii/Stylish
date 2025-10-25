package com.yogesh.stylish.data.local.data_store.cart 

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yogesh.stylish.domain.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.SerializationException 

private val Context.cartDataStore: DataStore<Preferences> by preferencesDataStore("cart_preferences")

class CartDataStore(private val context: Context) {

    companion object {
        val CART_ITEMS_KEY = stringPreferencesKey("cart_items_list")
    }

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val cartItems: Flow<List<CartItem>> = context.cartDataStore.data.map { preferences ->
        val itemsJson = preferences[CART_ITEMS_KEY] ?: "[]" 
        try {
            json.decodeFromString<List<CartItem>>(itemsJson)
        } catch (e: SerializationException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateCartItems(updatedItems: List<CartItem>) {
        context.cartDataStore.edit { preferences ->
            val itemsJson = json.encodeToString(updatedItems)
            preferences[CART_ITEMS_KEY] = itemsJson
        }
    }

    suspend fun clearCart() {
        context.cartDataStore.edit { preferences ->
            preferences[CART_ITEMS_KEY] = "[]" 
        }
    }
}