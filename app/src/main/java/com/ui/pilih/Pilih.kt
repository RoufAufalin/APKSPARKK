package com.ui.pilih

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ViewModelFactory
import com.data.Result
import com.example.bottomnavyt.MainActivity
import com.ui.bookedform.Form
import com.example.bottomnavyt.R
import com.example.bottomnavyt.databinding.ActivityPilihBinding
import com.ui.login.LoginViewModel

class pilih : AppCompatActivity() {


    data class Seat(var state: Int)

    companion object {
        const val BOOKED = 0
        const val AVAILABLE = 1
        const val SELECTED = 2
    }

    private lateinit var seats: MutableMap<String, Seat>
    private lateinit var seatImageViews: MutableMap<String, ImageView>
    private lateinit var binding: ActivityPilihBinding
    private var currentlySelectedSeat: Pair<String, ImageView>? = null
    var btnparkir: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding  = ActivityPilihBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val pilihViewModel: PilihViewModel by viewModels {
            viewModelFactory
        }


        btnparkir = binding.btnpilih
        btnparkir?.setOnClickListener(View.OnClickListener {
            val i = Intent(this@pilih, Form::class.java)
            startActivity(i)
            finish()
        })

        seats = mutableMapOf()
        seatImageViews = mutableMapOf()

        // Initialize seats
        initializeSeats()

        // Initialize ImageViews
        initializeSeatImageViews()

        // Set initial seat images based on their state
        setInitialSeatImages()

        // Set up click listeners
        setupClickListeners()


        pilihViewModel.getSlotParkir()

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
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    private fun initializeSeats() {
        for (i in 1..51) {
            val seatId = "seat_$i"
            seats[seatId] = Seat(AVAILABLE)
        }
    }

    private fun initializeSeatImageViews() {
        for (i in 1..51) {
            val seatId = "seat_$i"
            val resId = resources.getIdentifier(seatId, "id", packageName)
            seatImageViews[seatId] = findViewById(resId)
        }
    }


    private fun setInitialSeatImages() {
        for ((seatId, seat) in seats) {
            val seatImageView = seatImageViews[seatId] ?: continue
            when (seat.state) {
                BOOKED -> seatImageView.setImageResource(R.drawable.booked_img)
                AVAILABLE -> seatImageView.setImageResource(R.drawable.available_img)
                SELECTED -> seatImageView.setImageResource(R.drawable.your_seat_img)
            }
        }
    }

    private fun setupClickListeners() {
        for ((seatId, seatImageView) in seatImageViews) {
            seatImageView.setOnClickListener {
                val seat = seats[seatId] ?: return@setOnClickListener
                when (seat.state) {
                    BOOKED -> {
                        // Do nothing or show a message
                    }
                    AVAILABLE -> {
                        currentlySelectedSeat?.let {
                            // Reset previously selected seat to available
                            val (previousSeatId, previousImageView) = it
                            seats[previousSeatId]?.state = AVAILABLE
                            previousImageView.setImageResource(R.drawable.available_img)
                        }
                        // Update the newly selected seat
                        seat.state = SELECTED
                        seatImageView.setImageResource(R.drawable.your_seat_img)
                        currentlySelectedSeat = Pair(seatId, seatImageView)
                    }
                    SELECTED -> {
                        // Deselect the currently selected seat
                        seat.state = AVAILABLE
                        seatImageView.setImageResource(R.drawable.available_img)
                        currentlySelectedSeat = null
                    }
                }
            }
        }
    }
}
