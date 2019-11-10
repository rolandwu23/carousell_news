package com.grok.akm.carousell.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.grok.akm.carousell.R
import com.grok.akm.carousell.Utils.Utility
import com.grok.akm.carousell.model.News

class NewsAdapter (private val context: Context, private val newsList:List<News>?) : RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.news, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
       return newsList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val news = newsList?.get(position)

        holder.title.text = news?.title
        holder.description.text = news?.description
        holder.timeStamp.text = Utility.getTimeAgo(news?.date,context)

        val options = RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .priority(Priority.HIGH)

        Glide.with(context)
            .load(news?.bannerUrl)
            .apply(options)
            .into(holder.banner)

    }


    inner class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        var banner : ImageView = root.findViewById<View>(R.id.banner_iv) as ImageView
        var title : TextView = root.findViewById<View>(R.id.title_tv) as TextView
        var description : TextView = root.findViewById<View>(R.id.description_tv) as TextView
        var timeStamp : TextView = root.findViewById<View>(R.id.timeStamp_tv) as TextView
    }

}