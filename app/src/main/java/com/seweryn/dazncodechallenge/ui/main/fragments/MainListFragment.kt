package com.seweryn.dazncodechallenge.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seweryn.dazncodechallenge.R
import com.seweryn.dazncodechallenge.ui.base.BaseFragment
import com.seweryn.dazncodechallenge.ui.main.events.EventsRecyclerAdapter
import com.seweryn.dazncodechallenge.viewmodel.main.MainViewModel
import com.seweryn.dazncodechallenge.viewmodel.main.events.EventsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_events.view.*

abstract class MainListFragment<T: MainViewModel> : BaseFragment<T>() {

    private val adapter: EventsRecyclerAdapter = EventsRecyclerAdapter()

    /*private lateinit var viewModel: MainViewModel

    abstract fun viewModel(): MainViewModel*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = viewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        view.events_recyclerview.layoutManager = LinearLayoutManager(context)
        view.events_recyclerview.adapter = adapter
        return view
    }

    override fun onStart() {
        super.onStart()
        observeEvents()
        observeAction()
        viewModel.start()
    }

    private fun observeEvents() {
        viewModel.content.observe(this,
            Observer<List<MainViewModel.Content>> {
                adapter.updateEvents(it)
            }
        )
    }

    private fun observeAction() {
        viewModel.action.observe(this,
            Observer { action ->
                when(action) {
                    is MainViewModel.Action.PlayVideo -> {}
                }
            }
        )
    }
}