package com.grok.akm.carousell.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject


class ViewModelFactory
@Inject
constructor(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(FragmentViewModel::class.java)) {
            return FragmentViewModel() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}