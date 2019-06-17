package com.example.letmeknow

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {


    fun getRetrofit(): ApiEvents {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://letmeknow-dacf2.firebaseio.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiEvents::class.java)
    }
}