package com.ui.pilihparkir

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottomnavyt.R
import com.example.bottomnavyt.databinding.FragmentBagianDepanBinding

class BagianDepan : Fragment() {
    private var _binding: FragmentBagianDepanBinding? = null
    private val binding get() = _binding!!


    private enum class ParkingSlotStatus {
        EMPTY, CLICKED, BOOKED
    }

    private val seatStatus = mutableMapOf<Int, ParkingSlotStatus>()
    private val clickCount = mutableMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentBagianDepanBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val view = binding.root

        setupSeats()
        return view
    }

    private fun setupSeats(){
        val seats = mutableListOf<View>()
        for (i in 1..24) {
            val seatId = resources.getIdentifier("seat_$i", "id", requireContext().packageName)
            val seatView = binding.root.findViewById<View>(seatId)
            seats.add(seatView)

            seatStatus[i] = ParkingSlotStatus.EMPTY

            seatView.setOnClickListener {
                handleSeatClick(i, seatView)
            }
        }

    }

    private fun handleSeatClick(i: Int, seat: View) {
        when (seatStatus[i]){
            ParkingSlotStatus.EMPTY -> {
                seatStatus[i] = ParkingSlotStatus.CLICKED
                seat.setBackgroundResource(R.drawable.slot_available)
            }
            ParkingSlotStatus.CLICKED -> {
                seatStatus[i] = ParkingSlotStatus.BOOKED
                seat.setBackgroundResource(R.drawable.slot_empty)
            }
            ParkingSlotStatus.BOOKED -> {

            }

            else -> {}
        }

    }


    private fun updateSeatBackground(seat: View, status: ParkingSlotStatus) {
        when (status) {
            ParkingSlotStatus.EMPTY -> seat.setBackgroundResource(R.drawable.slot_empty)
            ParkingSlotStatus.CLICKED -> seat.setBackgroundResource(R.drawable.slot_available)
            ParkingSlotStatus.BOOKED -> seat.setBackgroundResource(R.drawable.slot_booked)
        }
    }
}