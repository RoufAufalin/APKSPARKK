package com.ui.pilihparkir

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ViewModelFactory
import com.data.Result
import com.data.response.BelakangItem
import com.data.response.DepanItem
import com.example.bottomnavyt.R
import com.example.bottomnavyt.databinding.FragmentBagianBelakangBinding
import com.ui.bookedform.Form
import com.ui.pilih.pilih

class BagianBelakang : Fragment() {

    private var _binding : FragmentBagianBelakangBinding? = null
    private val binding get() = _binding!!

    private lateinit var pilihParkirViewModel: PilihParkirViewModel

    private enum class ParkingSlotStatus {
        EMPTY, CLICKED, BOOKED
    }

    private val seatStatus = mutableMapOf<Int, ParkingSlotStatus>()
    private var selectedSeatId: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentBagianBelakangBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pilihParkirViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireContext())
        )[PilihParkirViewModel::class.java]

        setupSeats()
        observeViewModel()

        binding.btnNext.setOnClickListener {
            if (selectedSeatId != null) {
                proceedToNextActivity(selectedSeatId!!)
            } else {
                Toast.makeText(requireContext(), "Please select a seat first", Toast.LENGTH_SHORT).show()
            }
        }




    }


    private fun setupSeats() {
        for (i in 25..49) {
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

        pilihParkirViewModel.hasil.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    val blokResponse = result.data.belakang
                    updateSeatsBasedOnData(blokResponse)
                    binding.progressBar.visibility = View.GONE
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        pilihParkirViewModel.getSeatStatus()
    }

    private fun updateSeatsBasedOnData(depanItems: List<BelakangItem?>?) {
        depanItems?.forEach { data ->
            val seatIdFromApi = data?.id ?: return@forEach
            val status = data.status

            val seatViewId = resources.getIdentifier("seat_$seatIdFromApi", "id", requireContext().packageName)
            val seatView = binding.root.findViewById<View>(seatViewId)

            if (status == "Terisi" || status == "Dibooking") {
                seatView?.setBackgroundResource(R.drawable.slot_booked)
                seatStatus[seatIdFromApi] = ParkingSlotStatus.BOOKED
            }

            else {
                seatView?.setBackgroundResource(R.drawable.slot_empty)
                seatStatus[seatIdFromApi] = ParkingSlotStatus.EMPTY
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

        if (seatStatus[seatIndex] == ParkingSlotStatus.BOOKED) {
            Toast.makeText(requireContext(), "This slot is already booked", Toast.LENGTH_SHORT).show()
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
        val intent = Intent(requireContext(), Form::class.java)
        intent.putExtra("SELECTED_SEAT_ID", seatId)
        startActivity(intent)
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}