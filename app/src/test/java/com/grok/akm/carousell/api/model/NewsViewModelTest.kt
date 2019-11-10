package com.grok.akm.carousell.api.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.grok.akm.carousell.ViewModel.NewsViewModel
import com.grok.akm.carousell.ViewModel.Repository
import com.grok.akm.carousell.api.Status
import com.grok.akm.carousell.model.ApiResponse
import com.grok.akm.carousell.model.News
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*


@RunWith(JUnit4::class)
class NewsViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule =  InstantTaskExecutorRule()


    @Mock
    lateinit var repository: Repository

    @InjectMocks
    lateinit var viewModel: NewsViewModel

    @Mock
    lateinit var observer:Observer<ApiResponse>

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpClass(){
            RxAndroidPlugins.setInitMainThreadSchedulerHandler{Schedulers.trampoline()}
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        }
    }


    @Before
    @Throws(Exception::class)
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        viewModel.getRecentNewsLiveData().observeForever(observer)
    }

    @Test
    fun testNull() {
        `when`(repository.getNews()).thenReturn(null)
        assertNotNull(viewModel.getRecentNewsLiveData())
        assertTrue(viewModel.getRecentNewsLiveData().hasObservers())
    }

    @Test
    fun testApiFetchDataSuccess() {

        val news1 = News("1","a","b","c",123,1)
        val news = Arrays.asList(news1)

        `when`(repository.getNews()).thenReturn(Observable.just(news))

        viewModel.getRecentNews()
        viewModel.getRecentNewsLiveData().observeForever{ assertEquals(Status.LOADING,it.status)}
        viewModel.getRecentNewsLiveData().observeForever{ assertEquals(Status.SUCCESS,it.status)}
    }

    @Test
    fun testApiFetchDataError() {
        `when`(repository.getNews()).thenReturn(Observable.error(Throwable("Api Error")))
        viewModel.getRecentNews()

        viewModel.getRecentNewsLiveData().observeForever{ assertEquals(Status.LOADING,it.status)}
        viewModel.getRecentNewsLiveData().observeForever{ assertEquals(Status.ERROR,it.status)}
    }

}

