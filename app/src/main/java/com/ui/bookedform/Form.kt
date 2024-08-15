package com.ui.bookedform

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomnavyt.ReceiptActivity
import java.util.Calendar
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ViewModelFactory
import com.example.bottomnavyt.databinding.ActivityFormBinding
import com.data.Result
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Form : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private lateinit var bookingViewModel: FormViewModel

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

        binding.submitButton.setOnClickListener {
            val platNomor = binding.nomorPlatField.text.toString()
            val namaPemesan = binding.namaPemesanField.text.toString()
            val jenisMobil = binding.jenisMobilField.text.toString()
            val idSlot = binding.tempatParkirField.text.toString()

            bookingViewModel.bookSlot(
                platNomor = platNomor,
                namaPemesan = namaPemesan,
                jenisMobil = jenisMobil,
                idSlot = idSlot
            )
        }
    }

    private fun observeViewModel() {
        bookingViewModel.bookingResponse.observe(this, Observer { result ->
            binding.progressBar.visibility = View.GONE
            if (result != null) {
                val intent = Intent(this, ReceiptActivity::class.java).apply {
                    putExtra("NOMOR_TRANSAKSI", generateTransactionNumber())
                    putExtra("NOMOR_PLAT", binding.nomorPlatField.text.toString())
                    putExtra("NAMA_PEMESAN", binding.namaPemesanField.text.toString())
                    putExtra("JENIS_MOBIL", binding.jenisMobilField.text.toString())
                    putExtra("TEMPAT_PARKIR", binding.tempatParkirField.text.toString())
                    putExtra("TANGGAL", result.data?.tanggalMasuk.toString())
                }
                Toast.makeText(this, result.status, Toast.LENGTH_LONG).show()
                startActivity(intent)
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

//    fun parseDate(dateString: String): Date? {
//        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//        return format.parse(dateString)
//    }
}
