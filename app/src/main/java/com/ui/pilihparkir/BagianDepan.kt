package com.ui.pilihparkir

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ViewModelFactory
import com.data.Result
import com.data.Repository
import com.data.response.BlokResponse
import com.example.bottomnavyt.R
import com.example.bottomnavyt.databinding.FragmentBagianDepanBinding
import com.ui.pilih.PilihViewModel

class BagianDepan : Fragment() {
    private var _binding: FragmentBagianDepanBinding? = null
    private val binding get() = _binding!!

    private lateinit var pilihParkirViewModel: PilihParkirViewModel

    private enum class ParkingSlotStatus {
        EMPTY, CLICKED, BOOKED
    }

    private val seatStatus = mutableMapOf<Int, ParkingSlotStatus>()
    private var selectedSeatId: Int? = null // Simpan seat yang sedang dipilih

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentBagianDepanBinding.inflate(inflater, container, false)
        val view = binding.root

        pilihParkirViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireContext())
        )[PilihParkirViewModel::class.java]

        setupSeats()
        observeViewModel()

        // Add a button to proceed to the next activity
        binding.btnNext.setOnClickListener {
            if (selectedSeatId != null) {
                proceedToNextActivity(selectedSeatId!!)
            } else {
                Toast.makeText(requireContext(), "Please select a seat first", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSeats() {
        for (i in 1..24) {
            val seatId = resources.getIdentifier("seat_$i", "id", requireContext().packageName)
            val seatView = binding.root.findViewById<View>(seatId)

            seatStatus[i] = ParkingSlotStatus.EMPTY
            seatView.setBackgroundResource(R.drawable.slot_empty)

            seatView.setOnClickListener {
                handleSeatClick(i, seatView)
            }

        }
    }

    private fun observeViewModel() {
        pilihParkirViewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    val blokResponse = result.data
                    updateSeatsBasedOnData(blokResponse)
                    binding.progressBar.visibility = View.GONE
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        pilihParkirViewModel.getSlotBlok()
    }

    private fun updateSeatsBasedOnData(blokResponse: BlokResponse?) {
        blokResponse?.data?.forEach { data ->
            val seatId = data?.idBlok ?: return@forEach
            val status = data.status

            val seatView = binding.root.findViewById<View>(
                resources.getIdentifier("seat_$seatId", "id", requireContext().packageName)
            )

            if (status == "BOOKED") {
                seatView?.setBackgroundResource(R.drawable.slot_booked)
                seatStatus[seatId] = ParkingSlotStatus.BOOKED
            } else {
                seatView?.setBackgroundResource(R.drawable.slot_empty)
                seatStatus[seatId] = ParkingSlotStatus.EMPTY
            }
        }
    }

    private fun handleSeatClick(seatIndex: Int, seatView: View) {

        if (selectedSeatId != null && selectedSeatId != seatIndex) {
            val previousSeatViewId = resources.getIdentifier("seat_$selectedSeatId", "id", requireContext().packageName)
            val previousSeatView = binding.root.findViewById<View>(previousSeatViewId)
            previousSeatView?.setBackgroundResource(R.drawable.slot_empty)
            seatStatus[selectedSeatId!!] = ParkingSlotStatus.EMPTY
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
        val intent = Intent(requireContext(), com.ui.bookedform.Form::class.java)
        intent.putExtra("SELECTED_SEAT_ID", seatId)
        startActivity(intent)
    }
}
