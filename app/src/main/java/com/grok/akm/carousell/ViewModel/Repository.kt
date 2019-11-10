package com.grok.akm.carousell.ViewModel

import com.grok.akm.carousell.api.ApiCallInterface
import javax.inject.Inject

class Repository
@Inject constructor(private val apiCallInterface: ApiCallInterface){

    fun getNews() = apiCallInterface.getNews()

}