package com.ui.listbooked

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.data.response.DataItemz
import com.data.response.ListBookedResponse
import com.example.bottomnavyt.databinding.ItemListbookBinding
import com.ui.home.HomeAdapter

class BookedAdapter(val listBooked: List<DataItemz>): RecyclerView.Adapter<BookedAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    class ViewHolder(binding: ItemListbookBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvPlat = binding.platNomor
        val tvJenis = binding.tvJenisKendaraan

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookedAdapter.ViewHolder {
        val binding = ItemListbookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookedAdapter.ViewHolder, position: Int) {
        val item = listBooked[position]

        holder.tvPlat.text = item.platNomor
        holder.tvJenis.text = item.jenisMobil
    }

    override fun getItemCount(): Int = listBooked.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataItemz)
    }
}