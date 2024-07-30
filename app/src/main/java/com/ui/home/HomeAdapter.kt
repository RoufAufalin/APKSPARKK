package com.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.data.listItemHome
import com.example.bottomnavyt.databinding.ItemHomeBinding

class HomeAdapter(private val listItem: ArrayList<listItemHome>, private val listener: OnItemClickListener): RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {
    class ListViewHolder(binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvPlace = binding.tvPlace
        val tvType = binding.tvType
        val tvLocation = binding.tvLocation

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listItem[position]

        holder.tvType.text = item.type
        holder.tvPlace.text = item.place
        holder.tvLocation.text = item.location

        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: listItemHome)
    }

}