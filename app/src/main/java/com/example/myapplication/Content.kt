package com.example.myapplication

import org.parceler.Parcel
import org.parceler.ParcelConstructor


@Parcel(Parcel.Serialization.BEAN)
data class LogDHT @ParcelConstructor constructor(
        var id: String,
        var humidity: Float,
        var temperature: Float,
        var time: String) {
    constructor() : this("", 0f, 0f, "")
}


@Parcel(Parcel.Serialization.BEAN)
data class LogSEN @ParcelConstructor constructor(
        var id: String,
        var eld1: Int,
        var eld2: Int,
        var time: String) {
    constructor() : this("", 0, 0, "")
}

@Parcel(Parcel.Serialization.BEAN)
data class Update @ParcelConstructor constructor(
        val updateType: String?,
        var logDHT: LogDHT?,
        var logSEN: LogSEN?) {
    constructor() : this(null, null, null)

    class TYPE {
        companion object {
            val DHT = "LogDHT"
            val SEN = "LogSEN"
        }
    }
}