package com.sagar.mvvmbelalapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sagar.mvvmbelalapp.data.network.MyApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun userLogin(email: String, password: String): LiveData<String> {

        //created mutablelivedata. reason livedata is abstract class so can't create instant.
        val loginResponse = MutableLiveData<String>()
        MyApi().userLogin(email, password)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    loginResponse.value = t.message
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    loginResponse.value = if (response.isSuccessful) {
                        response.body()?.string()
                    } else {
                        response.errorBody()?.string()
                    }
                }
            })
        return loginResponse
    }
}