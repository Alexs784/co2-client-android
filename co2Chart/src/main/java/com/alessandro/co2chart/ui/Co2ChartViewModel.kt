package com.alessandro.co2chart.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessandro.co2chart.result.Co2Result
import com.alessandro.co2chart.usecase.GetLatestCo2DataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Co2ChartViewModel(
    private val getLatestCo2DataUseCase: GetLatestCo2DataUseCase
) : ViewModel() {

    private val ioContext = viewModelScope.coroutineContext + Dispatchers.IO
    private val co2LiveData = MutableLiveData<Co2Result>()

    fun co2LiveData(): LiveData<Co2Result> = co2LiveData

    fun loadLatestCo2Data() {
        viewModelScope.launch {
            co2LiveData.value = Co2Result.Loading(true)
            val result = withContext(ioContext) {
                getLatestCo2DataUseCase.execute()
            }
            co2LiveData.value = result
            co2LiveData.value = Co2Result.Loading(false)
        }
    }
}