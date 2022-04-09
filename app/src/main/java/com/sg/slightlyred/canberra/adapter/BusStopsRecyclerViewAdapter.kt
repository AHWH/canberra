package com.sg.slightlyred.canberra.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sg.slightlyred.canberra.data.model.bus.BusStop
import com.sg.slightlyred.canberra.databinding.RvRowItemStopBinding

class BusStopsRecyclerViewAdapter(private val busStops: List<BusStop>) : RecyclerView.Adapter<BusStopsRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val viewBinding: RvRowItemStopBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvRowItemStopBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.viewBinding) {
            with(busStops[position]) {
                rowItemStopHeader.text = description
                rowItemStopSubtitle.text = busStopCode
                rowItemStopText.text = roadName
            }
        }
    }

    override fun getItemCount(): Int = busStops.size
}