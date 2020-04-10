package com.seweryn.codechallenge.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.seweryn.codechallenge.R
import com.seweryn.codechallenge.ui.main.fragments.events.EventsFragment
import com.seweryn.codechallenge.ui.main.fragments.schedule.ScheduleFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val EVENTS_FRAGMENT_TAG = "events"
    private val SCHEDULE_FRAGMENT_TAG = "schedule"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            initialiseFragment(EventsFragment(), EVENTS_FRAGMENT_TAG, true)
            initialiseFragment(ScheduleFragment(), SCHEDULE_FRAGMENT_TAG, false)
        }
        bottom_navigation_view.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.action_events -> showFragment(EVENTS_FRAGMENT_TAG)
                R.id.action_schedule -> showFragment(SCHEDULE_FRAGMENT_TAG)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    fun initialiseFragment(fragment: Fragment, tag: String, isMainFragment: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, fragment, tag)
            if(!isMainFragment) hide(fragment)
            commit()
        }
    }

    private fun showFragment(fragmentTag: String) {
        supportFragmentManager.apply {
            findFragmentByTag(fragmentTag)?.let {fragment ->
                fragments.forEach {
                    beginTransaction().hide(it).commit()
                }
                beginTransaction().show(fragment).commit()
            }
        }
    }
}
