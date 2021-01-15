package com.info.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.info.kotlincountries.R
import com.info.kotlincountries.adapter.CountryAdapter
import com.info.kotlincountries.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    private lateinit var viewModel : FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        rv_country_list.layoutManager = LinearLayoutManager(context)
        rv_country_list.adapter = countryAdapter

        swipe_refresh_layout.setOnRefreshListener {
            rv_country_list.visibility = View.GONE
            tv_error_message.visibility = View.GONE
            pb_country_loading.visibility = View.GONE
            viewModel.refreshFromAPI()
            swipe_refresh_layout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                rv_country_list.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    tv_error_message.visibility = View.VISIBLE
                    pb_country_loading.visibility = View.GONE
                    rv_country_list.visibility = View.GONE
                } else {
                    tv_error_message.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    pb_country_loading.visibility = View.VISIBLE
                    tv_error_message.visibility = View.GONE
                    rv_country_list.visibility = View.GONE
                } else {
                    pb_country_loading.visibility = View.GONE
                }
            }
        })
    }

}