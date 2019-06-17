package com.example.letmeknow.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Event() : Parcelable {

    @SerializedName("EtkinlikAdi")
    var etkinlikAdi: String? = null

    @SerializedName("EtkinlikFoto")
    var etkinlikFoto: String? = null

    @SerializedName("EtkinlikAciklama")
    var etkinlikAciklama: String? = null

    @SerializedName("Organizator")
    var organizator: String? = null

    @SerializedName("Adres")
    var adres: String? = null

    @SerializedName("Tarih")
    var date: String? = null


    @SerializedName("sdada")
    private var ornek: Int = 0

    constructor(parcel: Parcel) : this() {
        etkinlikAdi = parcel.readString()
        etkinlikFoto = parcel.readString()
        etkinlikAciklama = parcel.readString()
        organizator = parcel.readString()
        adres = parcel.readString()
        date = parcel.readString()
        ///
        ornek = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(etkinlikAdi)
        parcel.writeString(etkinlikFoto)
        parcel.writeString(etkinlikAciklama)
        parcel.writeString(organizator)
        parcel.writeString(adres)
        parcel.writeString(date)
        //
        parcel.writeInt(ornek)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }

}