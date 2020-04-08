package com.seweryn.dazncodechallenge.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.seweryn.dazncodechallenge.ui.main.events.EventsFragment
import com.seweryn.dazncodechallenge.ui.main.schedule.ScheduleFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ) {

    private val NUMBER_OF_PAGES = 2

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> EventsFragment()
            else -> ScheduleFragment()
        }
    }

    override fun getCount(): Int = NUMBER_OF_PAGES
}