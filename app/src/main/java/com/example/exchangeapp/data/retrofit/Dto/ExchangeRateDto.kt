package com.example.exchangeapp.data.retrofit.Dto

import com.example.exchangeapp.domain.model.ExchangeRateData
import com.google.gson.annotations.SerializedName

data class ExchangeRateDto(
    @SerializedName("success"   ) var success   : Boolean? = null,
    @SerializedName("terms"     ) var terms     : String?  = null,
    @SerializedName("privacy"   ) var privacy   : String?  = null,
    @SerializedName("timestamp" ) var timestamp : Int?     = null,
    @SerializedName("source"    ) var source    : String?  = null,
    @SerializedName("quotes"    ) var quotes    : Quotes?  = Quotes()
){
    fun toExchangeRateData():ExchangeRateData = ExchangeRateData(timestamp, quotes)

}