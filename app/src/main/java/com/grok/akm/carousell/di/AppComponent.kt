package com.grok.akm.carousell.di

import com.grok.akm.carousell.View.MainActivity
import com.grok.akm.carousell.View.SortingDialogFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, UtilModule::class, SortOptionsModule::class])
@Singleton
interface AppComponent {

    fun doInjection(mainActivity: MainActivity)

    fun doInjection(sortingDialogFragment: SortingDialogFragment)
}