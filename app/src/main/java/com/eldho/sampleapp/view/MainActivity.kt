package com.eldho.sampleapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.eldho.sampleapp.R
import com.eldho.sampleapp.model.ViewItem
import com.eldho.sampleapp.viewmodel.MainActivityViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var listViews: ArrayList<ViewItem>
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var adapterRV: MoviesAdapter
    private var currentIndex: Int = 0

    private lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel =
            ViewModelProvider(this).get(MainActivityViewModel::class.java)

        initUI()

        setObservers()
        init()

    }

    private fun init(){
        mainActivityViewModel.getMoviesApi()


        search_view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapterRV.filter.filter(s.toString())
            }
        })
    }

    private fun setObservers() {
        mainActivityViewModel.getMoviesApiObservable().observe(
            this,
            {


                recycler_view.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                adapterRV = it.Search?.let { it1 -> MoviesAdapter(it1) }!!
                recycler_view.adapter = adapterRV


            }
        )


        mainActivityViewModel.getProgressObservable().observe(
            this,
            {
                if (it) {
                    Log.e("PROGRESS", "TRUE")
                    progress_bar.visibility = View.VISIBLE
                } else {
                    Log.e("PROGRESS", "FALSE")
                    progress_bar.visibility = View.GONE
                }
            }
        )
    }


    private fun initUI() {
        listViews = ArrayList()
        listViews.add(ViewItem(1, R.drawable.sample_img))
        listViews.add(ViewItem(2, R.drawable.sample_img))
        listViews.add(ViewItem(3, R.drawable.sample_img))
        listViews.add(ViewItem(4, R.drawable.sample_img))
        listViews.add(ViewItem(5, R.drawable.sample_img))

        adapter = ViewPagerAdapter(applicationContext, listViews)
        view_pager.adapter = adapter
        addPageIndicators()
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                updatePageIndicator(position)
            }
        })
    }

    private fun addPageIndicators() {
        lyt_page_indicator.removeAllViews()
        for (i in listViews.indices) {
            val view = ImageView(applicationContext)
            view.setImageResource(R.drawable.dot_unselected)

            lyt_page_indicator.addView(view)
        }
        updatePageIndicator(currentIndex)
    }

    fun updatePageIndicator(position: Int) {
        var imageView: ImageView

        val lp =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        lp.setMargins(5, 0, 5, 0)
        for (i in 0 until lyt_page_indicator.childCount) {
            imageView = lyt_page_indicator.getChildAt(i) as ImageView
            imageView.layoutParams = lp
            if (position == i) {
                imageView.setImageResource(R.drawable.dot_selected)
            } else {
                imageView.setImageResource(R.drawable.dot_unselected)
            }
        }
    }
}
