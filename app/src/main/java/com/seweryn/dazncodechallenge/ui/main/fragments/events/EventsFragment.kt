package com.seweryn.dazncodechallenge.ui.main.fragments.events

import androidx.lifecycle.ViewModelProvider
import com.seweryn.dazncodechallenge.ui.main.fragments.MainListFragment
import com.seweryn.dazncodechallenge.viewmodel.main.events.EventsViewModel

class EventsFragment : MainListFragment<EventsViewModel>() {
    override fun viewModel() = ViewModelProvider(this, viewModelFactory).get(EventsViewModel::class.java)
}