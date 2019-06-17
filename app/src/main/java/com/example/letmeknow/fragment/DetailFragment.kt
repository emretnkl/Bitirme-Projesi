package com.example.letmeknow.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.bumptech.glide.Glide


import com.example.letmeknow.R
import com.example.letmeknow.Retrofit
import com.example.letmeknow.model.Event
import com.example.letmeknow.model.Task
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : AppCompatActivity() {

    public var event: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detail)


       val bundle = intent.extras
        val event: Task = bundle.getSerializable("Event") as Task

       //detail_date.text = event.date.eventDate
        detail_descriptions.text = event!!.etkinlikAciklama
        detail_location.text = event!!.adres
        detail_title.text = event!!.etkinlikAdi
        detail_date.text = event!!.tarih
        Glide.with(this).load(event!!.etkinlikFoto).into(detail_imageView)
    }


    private fun setUI(event: Event) {

        var data: List<Event>? = null

        Retrofit.getRetrofit()
            .getEvents()
            .subscribe({ t ->
                data = t.data
            }, {

            })


    }

    fun setText(textView: TextView, text: String) {
        textView.text = text
    }

}
