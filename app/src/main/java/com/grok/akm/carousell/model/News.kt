package com.grok.akm.carousell.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.grok.akm.carousell.Utils.Constants
import com.grok.akm.carousell.Utils.Utility
import java.util.*

data class News(
    @SerializedName("id") @Expose var id:String,
    @SerializedName("title") @Expose var title:String,
    @SerializedName("description") @Expose var description:String,
    @SerializedName("banner_url") @Expose var bannerUrl:String,
    @SerializedName("time_created") @Expose var timeStamp:Int,
    @SerializedName("rank") @Expose var rank:Int
){
    val date : Date
    get() = Utility.parseDateString(
            Utility.getDate(timeStamp.toLong()),
            Constants.DATETIME_FORMAT,
            TimeZone.getTimeZone("Etc/UTC")
        )
}