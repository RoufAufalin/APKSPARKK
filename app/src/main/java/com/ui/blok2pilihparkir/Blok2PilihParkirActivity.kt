package com.ui.blok2pilihparkir

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.ViewModelFactory
import com.example.bottomnavyt.R
import com.data.Result
import com.data.response.Dataa
import com.data.response.DepanItem
import com.example.bottomnavyt.databinding.ActivityBlok2PilihParkirBinding
import com.ui.bookedform.Form
import com.ui.pilihparkir.PilihParkirViewModel

class Blok2PilihParkirActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBlok2PilihParkirBinding
    private lateinit var viewModel: PilihParkirViewModel

    private enum class ParkingSlotStatus {
        EMPTY, CLICKED, BOOKED
    }

    private val seatStatus = mutableMapOf<Int, ParkingSlotStatus>()
    private var selectedSeatId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlok2PilihParkirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[PilihParkirViewModel::class.java]

        binding.tvLokasi.text = intent.getStringExtra("loc")

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        setupSeats()
        observeViewModel()
        viewModel.getSlotBlok()

        binding.btnNext.setOnClickListener {
            if(selectedSeatId != null) {
                proceedToNextActivity(selectedSeatId!!)
            } else {
                Toast.makeText(this, "Please select seat first!", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun observeViewModel() {
        viewModel.result.observe(this) { result ->
            when(result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val blokResponse = result.data.data
                    updateSeatsBasedOnData(blokResponse)
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }

        }
    }

    private fun updateSeatsBasedOnData(blokResponse: List<Dataa?>?) {
        blokResponse?.forEach { data ->
            val seatIdFromApi = data?.id
            val status = data?.status

            val seatViewId = resources.getIdentifier("seat_$seatIdFromApi", "id", packageName)
            val seatView = binding.root.findViewById<View>(seatViewId)

            if (status == "Terisi" || status == "Dibooking") {
                seatView?.setBackgroundResource(R.drawable.slot_booked)
                seatStatus[seatIdFromApi!!] = ParkingSlotStatus.BOOKED
            } else {
                seatView?.setBackgroundResource(R.drawable.slot_empty)
                seatStatus[seatIdFromApi!!] = ParkingSlotStatus.EMPTY
            }

        }

    }


    private fun setupSeats() {
        for (i in 50..56) {
            val seatId = resources.getIdentifier("seat_$i", "id", packageName)
            val seatView = binding.root.findViewById<View>(seatId)

            seatStatus[i] = ParkingSlotStatus.EMPTY

            seatView.setBackgroundResource(R.drawable.slot_empty)

            seatView.setOnClickListener {
                handleSeatClick(i, seatView)
            }
        }
    }

    private fun handleSeatClick(seatIndex: Int, seatView: View) {

        if (selectedSeatId != null && selectedSeatId != seatIndex) {
            val previousSeatViewId = resources.getIdentifier("seat_$selectedSeatId", "id", packageName)
            val previousSeatView = binding.root.findViewById<View>(previousSeatViewId)
            previousSeatView?.setBackgroundResource(R.drawable.slot_empty)
            seatStatus[selectedSeatId!!] = ParkingSlotStatus.EMPTY
        }

        if (seatStatus[seatIndex] == ParkingSlotStatus.BOOKED) {
            Toast.makeText(this, "This slot is already booked", Toast.LENGTH_SHORT).show()
            return
        }

        when (seatStatus[seatIndex]) {
            ParkingSlotStatus.EMPTY -> {
                seatStatus[seatIndex] = ParkingSlotStatus.CLICKED
                seatView.setBackgroundResource(R.drawable.slot_available)
                selectedSeatId = seatIndex
            }
            ParkingSlotStatus.CLICKED -> {
                seatStatus[seatIndex] = ParkingSlotStatus.EMPTY
                seatView.setBackgroundResource(R.drawable.slot_empty)
                selectedSeatId = null
            }
            ParkingSlotStatus.BOOKED -> {
            }
            null -> Unit
        }
    }

    private fun proceedToNextActivity(seatId: Int) {
        val intent = Intent(this, Form::class.java)
        intent.putExtra("SELECTED_SEAT_ID", seatId)
        startActivity(intent)
    }

}