package com.example.cpsproject.model

//import kotlinx.serialization.Serializable
import android.os.Parcel
import android.os.Parcelable


data class Patient(
    var name: String?,
    var surname: String?,
    var notes: String ?= null,
    var taxcode: String?,
    var birthdate: String?,

    // TODO variabili da sistemare:
    // var dominantHand: Hand,
    // var gender: Gender

    var phase: Int = 1) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
}
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(notes)
        parcel.writeString(taxcode)
        parcel.writeString(birthdate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Patient> {
        override fun createFromParcel(parcel: Parcel): Patient {
            return Patient(parcel)
        }

        override fun newArray(size: Int): Array<Patient?> {
            return arrayOfNulls(size)
        }
    }
}


