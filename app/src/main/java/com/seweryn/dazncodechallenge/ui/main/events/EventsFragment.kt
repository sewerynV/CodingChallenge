package com.seweryn.dazncodechallenge.ui.main.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seweryn.dazncodechallenge.R
import com.seweryn.dazncodechallenge.ui.main.fragments.MainListFragment
import com.seweryn.dazncodechallenge.viewmodel.main.MainViewModel
import com.seweryn.dazncodechallenge.viewmodel.main.events.EventsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_events.view.*

class EventsFragment : MainListFragment<EventsViewModel>() {
    override fun viewModel() = ViewModelProvider(this, viewModelFactory).get(EventsViewModel::class.java)

    /*@Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun viewModel(): MainViewModel = ViewModelProvider(this, viewModelFactory).get(EventsViewModel::class.java)*/
}