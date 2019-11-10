package com.grok.akm.carousell.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SortOptionsModule(private val context: Context) {

    @Provides
    @Singleton
    fun getSortPref() = SortPreferences(context)

}