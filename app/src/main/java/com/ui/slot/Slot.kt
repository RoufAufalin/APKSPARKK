package com.ui.slot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.bottomnavyt.R
import com.ui.pilih.pilih

class slot : AppCompatActivity() {
    private var Tabebuya2: CardView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slot)

        Tabebuya2 = findViewById<CardView>(R.id.tabebuya2)
        Tabebuya2?.setOnClickListener {
            val i = Intent(this, pilih::class.java) // Gunakan 'this' bukan 'activity'
            startActivity(i)
        }
    }
}
