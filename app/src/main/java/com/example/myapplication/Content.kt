package com.example.myapplication

import com.example.myapplication.MyData.TYPE.Companion.DHT
import com.example.myapplication.MyData.TYPE.Companion.SEN
import org.parceler.Parcel
import org.parceler.ParcelConstructor


@Parcel(Parcel.Serialization.BEAN)
data class LogDHT @ParcelConstructor constructor(
        var id: String,
        var humidity: Float,
        var temperature: Float,
        var time: String) : MyData {
    constructor() : this("", 0f, 0f, "")

    override fun getType(): String = DHT

    override fun getDataId(): String = id

    override fun getParam1(): String = humidity.toString()

    override fun getParam2(): String = temperature.toString()

    override fun getDataTime(): String = time
}


@Parcel(Parcel.Serialization.BEAN)
data class LogSEN @ParcelConstructor constructor(
        var id: String,
        var eld1: Int,
        var eld2: Int,
        var time: String) : MyData {
    constructor() : this("", 0, 0, "")

    override fun getType(): String = SEN

    override fun getDataId(): String = id

    override fun getParam1(): String = eld1.toString()

    override fun getParam2(): String = eld2.toString()

    override fun getDataTime(): String = time
}

interface MyData {
    class TYPE {
        companion object {
            val DHT = "LogDHT"
            val SEN = "LogSEN"
        }
    }

    fun getType(): String

    fun getDataId(): String

    fun getParam1(): String

    fun getParam2(): String

    fun getDataTime(): String
}