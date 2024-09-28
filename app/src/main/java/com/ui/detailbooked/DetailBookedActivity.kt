package com.ui.detailbooked

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ViewModelFactory
import com.data.Result
import com.example.bottomnavyt.R
import com.example.bottomnavyt.databinding.ActivityDetailBookedBinding

class DetailBookedActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBookedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory : ViewModelFactory = ViewModelFactory.getInstance(this)
        val detailViewModel : DetailBookedViewModel by viewModels {
            viewModelFactory

        }

        binding.btnLanjut.setOnClickListener {
            finish()
        }

        val idBooked = intent.getIntExtra(EXTRA_ID, 14)
        Log.d("id BOOKED", "$idBooked")

        if (idBooked!=null){
            detailViewModel.getBooked(idBooked.toInt())

            detailViewModel.bookedResponse.observe(this){ result ->
                when(result){
                    is Result.Success -> {

                        binding.progressBar.visibility = View.GONE

                        val response = result.data
                        binding.tvNamaPemesan.text = response.data?.namaPemesan
                        binding.platNomer.text = response.data?.platNomor
                        binding.tvJenisMobil.text = response.data?.jenisMobil
                        binding.tvWaktuBooking.text = response.data?.waktuBooking
                        binding.tvEndofBooking.text =response.data?.waktuBookingBerakhir

                    }

                    is Result.Loading -> {
                      binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Error -> {
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        } else {
            Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
        }


    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}