package com.example.bottomnavyt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.data.UserPreferences
import com.ui.login.Login

class splashscreen : AppCompatActivity() {
    private val SPLASH_TIME_OUT = 2000 // 4 detik

    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        // Buat Handler untuk menunda tindakan selama beberapa detik
        Handler().postDelayed({
            // Arahkan ke aktivitas berikutnya setelah jeda waktu selesai
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // Tutup aktivitas SplashScreen agar tidak bisa kembali lagi
        }, SPLASH_TIME_OUT.toLong())
    }


}