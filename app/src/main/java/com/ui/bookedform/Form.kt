package com.ui.bookedform

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomnavyt.ReceiptActivity
import java.util.Calendar
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ViewModelFactory
import com.example.bottomnavyt.databinding.ActivityFormBinding
import com.data.Result
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class Form : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private lateinit var bookingViewModel: FormViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        bookingViewModel = ViewModelProvider(this, factory).get(FormViewModel::class.java)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
//        binding.dateField.setOnClickListener {
//            showDatePickerDialog()
//        }
//
//        binding.timeRangeField.setOnClickListener {
//            showTimeRangePickerDialog()
//        }

        val idParkir = intent.getIntExtra("SELECTED_SEAT_ID", -1)

        Log.d("FormActivity", "Selected Seat ID: $idParkir")
        binding.tempatParkirField.setText(idParkir.toString())

        binding.submitButton.setOnClickListener {
            val platNomor = binding.nomorPlatField.text.toString()
            val namaPemesan = binding.namaPemesanField.text.toString()
            val jenisMobil = binding.jenisMobilField.text.toString()
            val idSlot = binding.tempatParkirField.text.toString()

            bookingViewModel.bookSlot(
                platNomor = platNomor,
//                namaPemesan = namaPemesan,
                jenisMobil = jenisMobil,
                idSlot = idSlot
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeViewModel() {
        bookingViewModel.bookingResponse.observe(this, Observer { result ->
            binding.progressBar.visibility = View.GONE

            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    val bookedResponse = result.data
                    bookedResponse?.let { data->
                        val tanggalBooking = parseDate(result.data.data?.waktuBooking.toString())
                        val bookingBerakhir = parseDate(result.data.data?.waktuBookingBerakhir.toString())
                        val intent = Intent(this, ReceiptActivity::class.java).apply {
                            putExtra("NOMOR_TRANSAKSI", generateTransactionNumber())
                            putExtra("NOMOR_PLAT", binding.nomorPlatField.text.toString())
                            putExtra("NAMA_PEMESAN", binding.namaPemesanField.text.toString())
                            putExtra("JENIS_MOBIL", binding.jenisMobilField.text.toString())
                            putExtra("TEMPAT_PARKIR", binding.tempatParkirField.text.toString())
                            putExtra("TANGGAL", tanggalBooking)
                            putExtra("BOOKING_END", bookingBerakhir)
                        }
                        Toast.makeText(this, result.data.status, Toast.LENGTH_LONG).show()
                        startActivity(intent)
                    }
                }

                is Result.Error -> {
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        })

        bookingViewModel.loading.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        bookingViewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

    }

//    private fun showDatePickerDialog() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val datePickerDialog = DatePickerDialog(
//            this,
//            { _, selectedYear, selectedMonth, selectedDay ->
//                val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
//                binding.dateField.setText(formattedDate)
//            },
//            year,
//            month,
//            day
//        )
//        datePickerDialog.show()
//    }
//
//    private fun showTimeRangePickerDialog() {
//        val calendar = Calendar.getInstance()
//        val hour = calendar.get(Calendar.HOUR_OF_DAY)
//        val minute = calendar.get(Calendar.MINUTE)
//
//        val timePickerDialogStart = TimePickerDialog(
//            this,
//            { _, selectedHour, selectedMinute ->
//                val formattedStartTime = String.format("%02d:%02d", selectedHour, selectedMinute)
//
//                val timePickerDialogEnd = TimePickerDialog(
//                    this,
//                    { _, endHour, endMinute ->
//                        val formattedEndTime = String.format("%02d:%02d", endHour, endMinute)
//                        val formattedTimeRange = "$formattedStartTime - $formattedEndTime"
//                        binding.timeRangeField.setText(formattedTimeRange)
//                    },
//                    hour,
//                    minute,
//                    true
//                )
//                timePickerDialogEnd.show()
//            },
//            hour,
//            minute,
//            true
//        )
//        timePickerDialogStart.show()
//    }

    private fun generateTransactionNumber(): String {
        return "TRX" + System.currentTimeMillis().toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDate(dateTimeString: String): String {
        // Parse the date-time string to LocalDateTime
        val localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME)

        // Define the desired output format (e.g., "dd-MM-yyyy HH:mm:ss")
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

        // Format LocalDateTime to string
        return localDateTime.format(formatter)
    }
}
