package com.grok.akm.carousell.model

import com.grok.akm.carousell.api.Status

data class ApiResponse(var status: Status?, var news: List<News>?, var error:Throwable?)

fun newsLoading() = ApiResponse(Status.LOADING,null,null)

fun newsSuccess(news: List<News>) = ApiResponse(Status.SUCCESS,news,null)

fun newsError(error: Throwable) = ApiResponse(Status.ERROR,null,error)