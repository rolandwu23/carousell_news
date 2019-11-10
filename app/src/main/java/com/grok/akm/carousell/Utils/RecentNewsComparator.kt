package com.grok.akm.carousell.Utils

import com.grok.akm.carousell.model.News

class RecentNewsComparator : Comparator<News> {

    override fun compare(news1: News, news2: News): Int {
        return news2.date.compareTo(news1.date)
    }

}