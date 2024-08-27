package com.example.bottomnavyt.customview.slot

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SlotParkir @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Paint untuk menggambar kotak
    private val paintBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLUE
        strokeWidth = 4f
    }

    private val paintFillSelected = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    private val paintFillBooked = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.LTGRAY
    }

    // Status tempat parkir
    private val slots = arrayListOf(
        Slot(id = 1, status = SlotStatus.EMPTY),
        Slot(id = 2, status = SlotStatus.EMPTY),
        Slot(id = 3, status = SlotStatus.SELECTED),
        Slot(id = 4, status = SlotStatus.BOOKED),
        Slot(id = 5, status = SlotStatus.EMPTY)
        // Tambahkan sesuai kebutuhan
    )

    // Ukuran dan jarak antar kotak
    private val slotSize = 100
    private val slotMargin = 16

    // Kelas data untuk menyimpan status slot
    data class Slot(val id: Int, var status: SlotStatus)

    enum class SlotStatus {
        EMPTY, SELECTED, BOOKED
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Gambarkan setiap slot parkir
        for (i in slots.indices) {
            val slot = slots[i]
            val left = i * (slotSize + slotMargin)
            val top = 0
            val right = left + slotSize
            val bottom = top + slotSize

            when (slot.status) {
                SlotStatus.EMPTY -> {
                    // Gambar kotak kosong
                    canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paintBorder)
                }
                SlotStatus.SELECTED -> {
                    // Gambar kotak dipilih
                    canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paintFillSelected)
                }
                SlotStatus.BOOKED -> {
                    // Gambar kotak terisi
                    canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paintFillBooked)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Deteksi klik pada slot
                val index = (event.x / (slotSize + slotMargin)).toInt()
                if (index in slots.indices && slots[index].status == SlotStatus.EMPTY) {
                    slots[index].status = SlotStatus.SELECTED
                    invalidate() // Meminta view untuk redraw
                }
            }
        }
        return super.onTouchEvent(event)
    }
}
