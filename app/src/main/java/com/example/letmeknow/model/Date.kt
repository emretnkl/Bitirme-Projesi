package com.example.letmeknow.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Date() :Parcelable {

    @SerializedName("Saat")
    var hour: String? = null

    @SerializedName("Tarih")
    var eventDate: String? = null

    constructor(parcel: Parcel) : this() {
        hour = parcel.readString()
        eventDate = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(hour)
        parcel.writeString(eventDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Date> {
        override fun createFromParcel(parcel: Parcel): Date {
            return Date(parcel)
        }

        override fun newArray(size: Int): Array<Date?> {
            return arrayOfNulls(size)
        }
    }
}