package com.codility

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.InputStreamReader

// PostEntity data class
data class PostEntity(
    val id: String,
    val authorId: String,
    val content: String,
    val viewCount: Int,
    val timestamp: String
)

// JsonParser class
class JsonParser {
    // Reuse Gson instance for efficiency
    private val gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create()
    
    // Cache the TypeToken to avoid recreating it
    private val listType = object : TypeToken<List<PostEntity>>() {}.type

    fun parse(inputStream: InputStream): List<PostEntity> {
        return try {
            // Use InputStreamReader directly for efficiency
            inputStream.use { stream ->
                InputStreamReader(stream).use { reader ->
                    val result = gson.fromJson<List<PostEntity>>(reader, listType)
                    result ?: emptyList()
                }
            }
        } catch (e: Exception) {
            // Return empty list in case of any parsing errors
            emptyList()
        }
    }
}