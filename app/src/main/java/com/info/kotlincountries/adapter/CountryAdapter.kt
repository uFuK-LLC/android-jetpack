package com.info.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.info.kotlincountries.R
import com.info.kotlincountries.databinding.ItemCountryBinding
import com.info.kotlincountries.model.Country
import com.info.kotlincountries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(private val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), ItemClickListener {

    class CountryViewHolder(var view : ItemCountryBinding) : RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = DataBindingUtil.inflate<ItemCountryBinding>(LayoutInflater.from(parent.context), R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.view.country  = countryList[position]
        holder.view.listener = this

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList(newCountryList : List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged() // refresh adapter
    }

    override fun onItemClicked(v: View) {
        val uuid = v.tv_country_uuid.text.toString()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryUuid = uuid.toInt())
        Navigation.findNavController(v).navigate(action)
    }
}