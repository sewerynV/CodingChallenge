package com.seweryn.dazncodechallenge.viewmodel.main

import androidx.lifecycle.MutableLiveData
import com.seweryn.dazncodechallenge.R
import com.seweryn.dazncodechallenge.utils.DateDifferenceUtil
import com.seweryn.dazncodechallenge.utils.DateFormatter
import com.seweryn.dazncodechallenge.utils.StringProvider
import com.seweryn.dazncodechallenge.viewmodel.BaseViewModel
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

abstract class MainViewModel(private val dateFormatter: DateFormatter) : BaseViewModel() {

    var eventsLoadingProgress: MutableLiveData<Boolean> = MutableLiveData()
    var eventsError: MutableLiveData<String?> = MutableLiveData()
    var content: MutableLiveData<List<Content>> = MutableLiveData()
    var action: MutableLiveData<Action?> = MutableLiveData()

    abstract fun start()

    protected fun parseError(error: Throwable) {

    }

    protected fun formatDate(date: LocalDateTime): String = dateFormatter.formatDateForDisplay(date)

    protected fun sendAction(actionToPerform: Action) {
        sendSingleEvent(action, actionToPerform)
    }

    data class Content(
        val title: String,
        val subtitle: String,
        val date: String,
        val imageUrl: String,
        val selectAction: () -> Unit = {})

    sealed class Action{
        class PlayVideo(val url: String): Action()
    }
}