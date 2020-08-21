package com.sagar.mvvmbelalapp.data.repository

import com.sagar.mvvmbelalapp.data.network.MyApi
import com.sagar.mvvmbelalapp.data.network.SafeApiRequest
import com.sagar.mvvmbelalapp.data.network.responses.AuthResponse

class UserRepository : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { MyApi().userLogin(email, password) }


        // return MyApi().userLogin(email, password)

        /*//created mutablelivedata. reason livedata is abstract class so can't create instant.
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
        return loginResponse*/
    }
}