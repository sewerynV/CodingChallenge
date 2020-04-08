package com.seweryn.dazncodechallenge.ui.main.schedule

import androidx.lifecycle.ViewModelProvider
import com.seweryn.dazncodechallenge.ui.main.fragments.MainListFragment
import com.seweryn.dazncodechallenge.viewmodel.main.MainViewModel
import com.seweryn.dazncodechallenge.viewmodel.main.schedule.ScheduleViewModel
import javax.inject.Inject

class ScheduleFragment : MainListFragment<ScheduleViewModel>() {
    override fun viewModel(): ScheduleViewModel = ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel::class.java)

    /*@Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory*/

    //override fun viewModel(): MainViewModel = ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel::class.java)

}