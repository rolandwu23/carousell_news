package com.grok.akm.carousell.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grok.akm.carousell.Utils.SortType

class FragmentViewModel : ViewModel() {

    private val statusLiveData = MutableLiveData<SortType>()

    fun setStatusLiveData(status:SortType) { statusLiveData.value = status }

    fun getStatusLiveData() = statusLiveData

}