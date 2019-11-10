package com.grok.akm.carousell.Utils

import com.grok.akm.carousell.model.News

class PopularNewsComparator : Comparator<News> {
    override fun compare(news1: News, news2: News): Int {
        val rankCompare = news1.rank.compareTo(news2.rank)
        if(rankCompare != 0) {
            return rankCompare
        }

        return news2.date.compareTo(news1.date)
    }

}