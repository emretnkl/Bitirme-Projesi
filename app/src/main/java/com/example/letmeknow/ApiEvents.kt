package com.example.letmeknow

import com.example.letmeknow.model.Event
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiEvents {

    @GET("/.json")
    fun getEvents() : Observable<EventResponse>

    @PUT("/data.json")
    fun putEvent(@Path("name") name: String, @Body event: Event)
}