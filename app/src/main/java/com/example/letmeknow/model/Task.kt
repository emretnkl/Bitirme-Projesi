package com.example.letmeknow.model

import java.io.Serializable

class Task() : Serializable {
    companion object Factory {
        fun create(): Task = Task()
    }

    var etkinlikAdi: String? = null
    var etkinlikFoto: String? = null
    var etkinlikAciklama: String? = null
    var organizator: String? = null
    var adres: String? = null
    var tarih: String? = null
    var objectId: String? = null
}