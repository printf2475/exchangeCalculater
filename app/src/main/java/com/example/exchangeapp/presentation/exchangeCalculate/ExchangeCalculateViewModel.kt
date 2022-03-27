package com.example.exchangeapp.presentation.exchangeCalculate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeapp.domain.useCase.GetUseCases
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ExchangeCalculateViewModel(val useCases: GetUseCases) : ViewModel() {
    //송금국가
    val _remittanceCountryLiveData = MutableLiveData<String>()
    val remittanceCountryLiveData: LiveData<String> = _remittanceCountryLiveData

    //수취국가
    val _recipientCountryLiveData = MutableLiveData<String>()
    val recipientCountryLiveData: LiveData<String> = _recipientCountryLiveData


    //환율
    val _exchangeRateLiveData = MutableLiveData<String>()
    val exchangeRateLiveData: LiveData<String> = _exchangeRateLiveData

    //조회시간
    val _inquiryTimeLiveData = MutableLiveData<String>()
    val inquiryTimeLiveData: LiveData<String> = _inquiryTimeLiveData

    //환전액
    val _exchangeAmountLiveData = MutableLiveData<String>()
    val exchangeAmountLiveData: LiveData<String> = _exchangeAmountLiveData

    val _outOfRangeLiveData = MutableLiveData<String>()
    val outOfRangeLiveData: LiveData<String> = _outOfRangeLiveData

    var exchangeRate = 0.00
    var selectedPosition = 0


    init {
        //databinding을 사용하여 따로 업데이트 하지않아도 데이터와 ui가 일치
        _remittanceCountryLiveData.value = MainActivity.CURRENT_COUNTRY
        _recipientCountryLiveData.value = MainActivity.itemList[selectedPosition]
        _exchangeAmountLiveData.value = ""
        getExchangeRate(0)
    }

    fun getExchangeRate(position: Int) {
        //환율을 Retrofit으로 받아와 Flow로 Rx프로그램 형식의 데이터 스트림을 받아온후 각 결과를 업데이트
        selectedPosition = position
        _recipientCountryLiveData.value = MainActivity.itemList[selectedPosition]
        CoroutineScope(Dispatchers.IO).launch {
            useCases.getExchange().onEach { exchangeRateData ->
                Log.i("값1", exchangeRateData.quotes.toString())

                if (exchangeRateData.timestamp != null) {
                    _inquiryTimeLiveData.postValue(convertTimestampToDate(exchangeRateData.timestamp))

                    when (position) {
                        0 -> {
                            val USDKRW = exchangeRateData.quotes?.USDKRW!!
                            _exchangeRateLiveData.postValue(
                                addticker(amountAddCommaAnddecimalpointSecond(USDKRW), position)
                            )
                            exchangeRate = USDKRW
                        }

                        1 -> {
                            val USDJPY = exchangeRateData.quotes?.USDJPY!!
                            _exchangeRateLiveData.postValue(
                                addticker(amountAddCommaAnddecimalpointSecond(USDJPY), position)
                            )
                            exchangeRate = USDJPY
                        }

                        2 -> {
                            val USDPHP = exchangeRateData.quotes?.USDPHP!!
                            _exchangeRateLiveData.postValue(
                                addticker(amountAddCommaAnddecimalpointSecond(USDPHP), position)
                            )
                            exchangeRate = USDPHP
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


    fun setRemittanceAmount(remittanceAmount: Long) {
        //송금액이 변경될때마다 ui업데이트
        if (exchangeRate != null) {

            var exchangeAmount = remittanceAmount * exchangeRate

            if (exchangeAmount < 10000 && exchangeAmount > 0) {
                _outOfRangeLiveData.value = ""

                _exchangeAmountLiveData.value = String.format(
                    "수취금액은 %s %s 입니다",
                    amountAddCommaAnddecimalpointSecond(exchangeAmount),
                    MainActivity.tickerList[selectedPosition]
                )

            } else if (exchangeAmount > 10000 || exchangeAmount < 0) {

                _outOfRangeLiveData.value = "송금액이 바르지 않습니다"

            } else {

                _exchangeAmountLiveData.value = ""

            }
        }
    }

    fun convertTimestampToDate(timestamp: Int): String =
        SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(timestamp * 1000L)).toString()

    fun amountAddCommaAnddecimalpointSecond(exchangeAmount: Double) =
        DecimalFormat("#,###.00").format(exchangeAmount)

    private fun addticker(exchangeRate: String, position: Int) =
        String.format(
            "%s %s/%s",
            exchangeRate,
            MainActivity.tickerList[position],
            MainActivity.tickerList[3]
        )


}