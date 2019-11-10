package com.grok.akm.carousell.View

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.grok.akm.carousell.R
import com.grok.akm.carousell.Utils.MarginItemDecoration
import com.grok.akm.carousell.Utils.SortType
import com.grok.akm.carousell.ViewModel.FragmentViewModel
import com.grok.akm.carousell.ViewModel.NewsViewModel
import com.grok.akm.carousell.ViewModel.ViewModelFactory
import com.grok.akm.carousell.api.Status
import com.grok.akm.carousell.di.MyApplication
import com.grok.akm.carousell.di.SortPreferences
import com.grok.akm.carousell.model.ApiResponse
import com.grok.akm.carousell.model.News
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var preferences: SortPreferences

    lateinit var adapter: NewsAdapter

    private var newsViewModel:NewsViewModel? = null

    private var fragmentViewModel: FragmentViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolbar()

        (application as MyApplication).appComponent!!.doInjection(this)

        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        activity_main_recycler_view.layoutManager = layoutManager
        activity_main_recycler_view.itemAnimator =DefaultItemAnimator()
        activity_main_recycler_view.addItemDecoration(MarginItemDecoration(resources.getDimension(
            R.dimen.default_padding
        ).toInt()))

        newsViewModel = ViewModelProviders.of(this,viewModelFactory).get(NewsViewModel::class.java)

        newsViewModel?.getRecentNewsLiveData()?.observe(this, Observer<ApiResponse> { consumeApiResponse(it) })

        newsViewModel?.getPopularNewsLiveData()?.observe(this, Observer<ApiResponse> { consumeApiResponse(it) })

        fragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(FragmentViewModel::class.java)

        fragmentViewModel?.getStatusLiveData()?.observe(this, Observer<SortType> { this.showSortOptions(it) })

        if(savedInstanceState == null) {
            when (preferences.selectedOption) {
                SortType.RECENT.value -> showRecentNews()
                SortType.POPULAR.value -> showPopularNews()
            }
        }
    }


    private fun setToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
    }

    private fun consumeApiResponse(apiResponse: ApiResponse?) {

        when (apiResponse?.status) {

            Status.LOADING -> {
                shimmer_view_container.visibility = View.VISIBLE
                shimmer_view_container.startShimmer()
            }

            Status.SUCCESS -> renderSuccessResponse(apiResponse.news)

            Status.ERROR -> {
                shimmer_view_container.stopShimmer()
                shimmer_view_container.visibility = View.GONE
                Toast.makeText(this, resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun renderSuccessResponse(news: List<News>?) {
        shimmer_view_container.stopShimmer()
        shimmer_view_container.visibility = View.GONE
        adapter = NewsAdapter(this, news)
        activity_main_recycler_view.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sort_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_sort -> displaySortOptions()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displaySortOptions(){
        val sortingDialogFragment =
            SortingDialogFragment.newInstance()
        sortingDialogFragment.show(supportFragmentManager, "Select Quantity")
    }

    private fun showSortOptions(sortType: SortType?) {
        when (sortType) {
            SortType.RECENT -> showRecentNews()
            SortType.POPULAR -> showPopularNews()
        }
    }

    private fun showRecentNews(){
        newsViewModel?.getRecentNews()
    }

    private fun showPopularNews(){
        newsViewModel?.getPopularNews()
    }
}


