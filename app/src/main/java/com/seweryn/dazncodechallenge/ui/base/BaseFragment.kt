package com.seweryn.dazncodechallenge.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seweryn.dazncodechallenge.viewmodel.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<T: BaseViewModel> : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: T

    abstract fun viewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = viewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

}