package com.sagar.mvvmbelalapp.data.network

import com.sagar.mvvmbelalapp.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        val response = call.invoke()

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.toString() ?: ""
            val message = StringBuilder()
            try {
                message.append(JSONObject(error).getString("message "))
            } catch (_: JSONException) {
                message.append("\n")
            }
            message.append("Error code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }
}