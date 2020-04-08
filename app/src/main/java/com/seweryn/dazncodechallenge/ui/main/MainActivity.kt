package com.seweryn.dazncodechallenge.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seweryn.dazncodechallenge.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(String.format("TEST KURWA %s", "dupa", "cipa"))
        main_viewpager.adapter = MainViewPagerAdapter(supportFragmentManager)

    }
}
