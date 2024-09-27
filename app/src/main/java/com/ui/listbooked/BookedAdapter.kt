package com.ui.listbooked

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.data.response.DataItemz
import com.data.response.ListBookedResponse
import com.example.bottomnavyt.databinding.ItemListbookBinding
import com.ui.home.HomeAdapter
import com.utils.DateUtils

class BookedAdapter(val listBooked: List<DataItemz>): RecyclerView.Adapter<BookedAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    class ViewHolder(binding: ItemListbookBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvPlat = binding.platNomor
        val tvJenis = binding.tvJenisKendaraan
        val tvDate = binding.tvDate
        val tvTime = binding.tvHour
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookedAdapter.ViewHolder {
        val binding = ItemListbookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookedAdapter.ViewHolder, position: Int) {
        val item = listBooked[position]

        val dateTime = item.waktuBooking?.split(" ")
        val waktu = dateTime?.get(0)
        val tanggal = dateTime?.get(1)


        holder.tvPlat.text = item.platNomor
        holder.tvJenis.text = item.jenisMobil
        holder.tvTime.text = waktu?.let { DateUtils.formatDate(it) }
        holder.tvDate.text = tanggal?.let { DateUtils.formatDate(it) }

        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(item)
        }
    }

    override fun getItemCount(): Int = listBooked.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataItemz)
    }
}