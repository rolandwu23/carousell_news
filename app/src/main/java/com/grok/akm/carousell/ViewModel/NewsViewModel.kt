package com.grok.akm.carousell.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grok.akm.carousell.Utils.PopularNewsComparator
import com.grok.akm.carousell.Utils.RecentNewsComparator
import com.grok.akm.carousell.model.ApiResponse
import com.grok.akm.carousell.model.newsError
import com.grok.akm.carousell.model.newsLoading
import com.grok.akm.carousell.model.newsSuccess
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsViewModel (private val repository: Repository) : ViewModel() {

    private var recentNews = MutableLiveData<ApiResponse>()
    private var popularNews = MutableLiveData<ApiResponse>()
    private var compositeDisposable = CompositeDisposable()

    fun getPopularNews() {

        compositeDisposable.add(repository.getNews()
            .flatMapIterable { news -> news }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .toSortedList(PopularNewsComparator())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { popularNews.postValue(newsLoading()) }
            .subscribe(
                {result -> popularNews.postValue(newsSuccess(result))},
                {throwable -> popularNews.postValue(newsError(throwable))}
            )
        )
    }

    fun getRecentNews(){

        compositeDisposable.add(repository.getNews()
            .flatMapIterable { news -> news }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .toSortedList(RecentNewsComparator())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { popularNews.postValue(newsLoading()) }
            .subscribe(
                {result -> popularNews.postValue(newsSuccess(result))},
                {throwable -> popularNews.postValue(newsError(throwable))}
            )
        )
    }

    fun getRecentNewsLiveData() : MutableLiveData<ApiResponse>{
        return recentNews
    }

    fun getPopularNewsLiveData() : MutableLiveData<ApiResponse>{
        return popularNews
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}