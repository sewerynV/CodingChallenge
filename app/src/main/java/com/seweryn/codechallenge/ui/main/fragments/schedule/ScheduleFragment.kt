package com.seweryn.codechallenge.ui.main.fragments.schedule

import androidx.lifecycle.ViewModelProvider
import com.seweryn.codechallenge.ui.main.fragments.MainListFragment
import com.seweryn.codechallenge.viewmodel.main.schedule.ScheduleViewModel

class ScheduleFragment : MainListFragment<ScheduleViewModel>() {
    override fun viewModel(): ScheduleViewModel = ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel::class.java)

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stop()
    }

}