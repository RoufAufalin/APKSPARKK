package com.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.data.listItemHome
import com.example.bottomnavyt.R
import com.example.bottomnavyt.databinding.FragmentHomeBinding
import com.ui.slot.slot

class Home : Fragment(), HomeAdapter.OnItemClickListener {
    private var Rektorat: CardView? = null
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listHome = getListHome()
        showItem(listHome)
    }

    private fun getListHome(): ArrayList<listItemHome> {
        val listHome = ArrayList<listItemHome>()

        val places = resources.getStringArray(R.array.data_parkiran)
        val types = resources.getStringArray(R.array.jenis_parkiran)
        val locations = resources.getStringArray(R.array.lokasi_parkiran)

        for (i in places.indices) {
            val item = listItemHome(places[i], types[i], locations[i])
            listHome.add(item)
        }
        return listHome
    }

    private fun showItem(listHome: List<listItemHome>) {
        binding.rvFakultas.layoutManager = LinearLayoutManager(context)
        val adapter = HomeAdapter(listHome as ArrayList<listItemHome>, this)
        binding.rvFakultas.adapter = adapter
    }

    override fun onItemClick(item: listItemHome) {
        val intent = Intent(activity, slot::class.java)
        // Jika Anda ingin mengirim data tambahan ke slot activity
        // intent.putExtra("extra_key", item.someProperty)
        startActivity(intent)
    }
}
