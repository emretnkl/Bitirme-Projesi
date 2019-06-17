package com.example.letmeknow

import com.example.letmeknow.model.Event
import com.google.gson.annotations.SerializedName

class EventResponse {

    @SerializedName("data")
    lateinit var data : List<Event>
}