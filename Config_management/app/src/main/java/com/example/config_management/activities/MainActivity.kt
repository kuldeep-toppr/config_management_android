package com.example.config_management.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.config_management.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val Domainbutton: Button = findViewById(R.id.domain_button)
        val Featurebutton: Button = findViewById(R.id.feature_button)

        domain_button.setOnClickListener {
            Log.i("MainActivity","Button was clicked")
            val intent =Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
         feature_button.setOnClickListener {
            Log.i("MainActivity","Button was clicked")
            val intent =Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
        val viewPager = findViewById(R.id.pager) as ViewPager
        val myPagerAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.setAdapter(myPagerAdapter)
        val tabLayout = findViewById(R.id.tablayout) as TabLayout
        tablayout.setUpWithViewPager(viewPager)
    }
}

