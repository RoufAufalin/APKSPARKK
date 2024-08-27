package com.ui.pilihparkir

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.bottomnavyt.R
import com.example.bottomnavyt.databinding.ActivityPilihParkirBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PilihParkirActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPilihParkirBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihParkirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPage: ViewPager2 = binding.viewPager
        viewPage.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabLayout

        TabLayoutMediator(tabs, viewPage) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val tempatParkir = binding.tvLokasi
        val intent = intent

        tempatParkir.text = intent.getStringExtra("loc")

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf (
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}