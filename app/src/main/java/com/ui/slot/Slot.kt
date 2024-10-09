package com.ui.slot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ViewModelFactory
import com.data.Result
import com.example.bottomnavyt.R
import com.example.bottomnavyt.databinding.ActivitySlotBinding
import com.ui.blok2pilihparkir.Blok2PilihParkirActivity
import com.ui.pilih.PilihViewModel
import com.ui.pilih.pilih
import com.ui.pilihparkir.PilihParkirActivity

class slot : AppCompatActivity() {
    private var Tabebuya2: CardView? = null

    private lateinit var binding: ActivitySlotBinding
    private lateinit var pilihViewModel: PilihViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        pilihViewModel = ViewModelProvider(this, viewModelFactory)[PilihViewModel::class.java]

        binding.btnBack.setOnClickListener{
            onBackPressed()
            finish()
        }

        slotBlokPertama()
        slotBlokKedua()



        Tabebuya2 = findViewById<CardView>(R.id.tabebuya2)
        Tabebuya2?.setOnClickListener {
            val i = Intent(this, PilihParkirActivity::class.java).apply {
                putExtra("loc", binding.parkir1.text.toString())
            }
            startActivity(i)
        }

        binding.blok2.setOnClickListener {
            val i = Intent(this, Blok2PilihParkirActivity::class.java).apply {
                putExtra("loc", binding.parkir2.text.toString())
            }
            startActivity(i)
        }
    }

    private fun slotBlokKedua() {
        pilihViewModel.getSlotParkir(2)

        pilihViewModel.result2.observe(this, Observer {result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        if (result != null) {
                            binding.slotparkir2.text = result.data.slotKosong.toString()
                        } else {
                            Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Result.Error -> {
//                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    private fun slotBlokPertama() {
        pilihViewModel.getSlotParkir(1)

        pilihViewModel.result.observe(this, Observer {result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        if (result != null) {
                            binding.slotparkir.text = result.data.slotKosong.toString()
                        } else {
                            Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Result.Error -> {
//                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }
}
