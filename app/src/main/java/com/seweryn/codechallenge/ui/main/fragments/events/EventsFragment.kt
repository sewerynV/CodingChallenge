package com.seweryn.codechallenge.ui.main.fragments.events

import androidx.lifecycle.ViewModelProvider
import com.seweryn.codechallenge.ui.main.fragments.MainListFragment
import com.seweryn.codechallenge.viewmodel.main.events.EventsViewModel

class EventsFragment : MainListFragment<EventsViewModel>() {
    override fun viewModel() = ViewModelProvider(this, viewModelFactory).get(EventsViewModel::class.java)
}