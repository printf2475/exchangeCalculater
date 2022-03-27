package com.example.exchangeapp.presentation.exchangeCalculate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.exchangeapp.R
import com.example.exchangeapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var binding: ActivityMainBinding? = null
    private lateinit var dataBinding: ActivityMainBinding

    //국가가 많아지면 이부분 하드코딩말고 방법이 없는가?
    //모든 국가의 정보를 map에 담아서 사용 -> 결국 한번은 고생해야되는건가? 해외에서 사용할땐?
    // R.value.string에서 사용하면 되나?
    companion object {
        const val CURRENT_COUNTRY = "미국(USD)"
        val itemList = listOf("한국(KRW)", "일본(JPY)", "필리핀(PHP)")
        val tickerList = listOf("KRW", "JPY", "PHP", "USD")
    }

    private val viewModel: ExchangeCalculateViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.viewmodel = viewModel
        dataBinding.setLifecycleOwner(this)

        //스피너 init
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, itemList)
        dataBinding.spinnerCountrySelection.adapter = adapter
        dataBinding.spinnerCountrySelection.onItemSelectedListener = this

        //송금액 입력시 값 수취금 변경
        dataBinding.edtRemittanceAmount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!text.isNullOrEmpty()) {
                    viewModel.setRemittanceAmount(text.toString().toLong())
                }else{
                    viewModel.setRemittanceAmount(0)
                }
            }

            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(text: Editable?) {}

        })

    }


    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //spinner에서 국가가 선택되었을때
        viewModel.getExchangeRate(position)
        dataBinding.edtRemittanceAmount.setText("")

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}


    override fun onDestroy() {
        super.onDestroy()
        //메모리 낭비 방지
        binding = null
    }


}