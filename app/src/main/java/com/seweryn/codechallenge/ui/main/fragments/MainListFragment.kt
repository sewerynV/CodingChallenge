package com.seweryn.codechallenge.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.seweryn.codechallenge.R
import com.seweryn.codechallenge.ui.base.BaseFragment
import com.seweryn.codechallenge.ui.extensions.hide
import com.seweryn.codechallenge.ui.extensions.show
import com.seweryn.codechallenge.ui.video.VideoActivity
import com.seweryn.codechallenge.viewmodel.main.MainViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.android.synthetic.main.fragment_main_list.view.*

abstract class MainListFragment<T: MainViewModel> : BaseFragment<T>() {

    private val adapter: MainListRecyclerAdapter = MainListRecyclerAdapter()
    private var skeletonProgress: SkeletonScreen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_list, container, false)
        view.content_recyclerview.layoutManager = LinearLayoutManager(context)
        view.content_recyclerview.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeContent()
        observeProgress()
        observeAction()
        observeErrors()
    }

    private fun observeContent() {
        viewModel.content.observe(viewLifecycleOwner,
            Observer<List<MainViewModel.ContentItem>> {
                error_container.hide()
                adapter.updateEvents(it)
            }
        )
    }

    private fun observeProgress() {
        viewModel.contentLoadingProgress.observe(viewLifecycleOwner,
            Observer { progress ->
                if(progress) {
                    skeletonProgress = Skeleton.bind(content_recyclerview)
                        .adapter(adapter)
                        .load(R.layout.item_event_skeleton)
                        .color(R.color.white_80)
                        .show()
                }else skeletonProgress?.hide()
            })
    }

    private fun observeErrors() {
        viewModel.contentError.observe(viewLifecycleOwner,
            Observer { error ->
                if(error == null) {
                    error_container.hide()
                } else {
                    error_container.show()
                    error_retry_button.setOnClickListener { error.retryAction.invoke() }
                    when(error) {
                        is MainViewModel.Error.NoInternetError -> error_message.text = resources.getString(R.string.error_message_no_internet)
                        else -> error_message.text = resources.getString(R.string.error_message_generic)
                    }

                }

            })
    }

    private fun observeAction() {
        viewModel.action.observe(viewLifecycleOwner,
            Observer { action ->
                when(action) {
                    is MainViewModel.Action.PlayVideo -> { context?.let { context -> context.startActivity(VideoActivity.createIntent(context, action.url)) } }
                }
            }
        )
    }
}