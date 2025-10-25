package com.yogesh.stylish.data.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.SerializationException


class StringListConverter {
    
    
    private val json = Json { ignoreUnknownKeys=true }
    
    @TypeConverter
    fun fromStringList(value: List<String>?):String{
        return value.let { json.encodeToString(it) }
    }

    @TypeConverter 
    fun toStringList(value: String): List<String>? { 
        return try {
            if (value.isBlank()) { 
                null 
            } else {
                json.decodeFromString<List<String>>(value)
            }
        } catch (e: SerializationException) {
            
            null 
        } catch (e: Exception) {
           
            null 
        }
    }
}