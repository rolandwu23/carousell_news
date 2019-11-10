package com.grok.akm.carousell.api

import com.grok.akm.carousell.model.News
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiCallInterface {

    @GET("carousell-interview-assets/android/carousell_news.json")
    fun getNews() : Observable<List<News>>

}