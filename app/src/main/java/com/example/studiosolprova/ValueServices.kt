package com.example.studiosolprova

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ValueServices {
  @GET("rand?min=1&max=300") fun getValue(): Call<ValueModel>
}