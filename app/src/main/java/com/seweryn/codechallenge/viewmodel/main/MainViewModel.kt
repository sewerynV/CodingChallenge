package com.seweryn.codechallenge.viewmodel.main

import androidx.lifecycle.MutableLiveData
import com.seweryn.codechallenge.tools.network.error.ConnectionError
import com.seweryn.codechallenge.utils.DateFormatter
import com.seweryn.codechallenge.utils.SchedulersProvider
import com.seweryn.codechallenge.viewmodel.BaseViewModel
import org.threeten.bp.LocalDateTime

abstract class MainViewModel(private val dateFormatter: DateFormatter, schedulersProvider: SchedulersProvider) : BaseViewModel(schedulersProvider) {

    var contentLoadingProgress: MutableLiveData<Boolean> = MutableLiveData()
    var contentError: MutableLiveData<Error?> = MutableLiveData()
    var content: MutableLiveData<List<ContentItem>> = MutableLiveData()
    var action: MutableLiveData<Action?> = MutableLiveData()

    abstract fun retry()

    protected fun parseError(error: Throwable) {
        contentError.value = when(error) {
            is ConnectionError -> Error.NoInternetError{ retry() }
            else -> Error.GenericError{ retry() }
        }
    }

    protected fun formatDate(date: LocalDateTime): String = dateFormatter.formatDateForDisplay(date)

    protected fun sendAction(actionToPerform: Action) {
        sendSingleEvent(action, actionToPerform)
    }

    data class ContentItem(
        val title: String,
        val subtitle: String,
        val date: String,
        val imageUrl: String,
        val selectAction: () -> Unit = {})

    sealed class Error(val retryAction: () -> Unit){
        class GenericError(retryAction: () -> Unit) : Error(retryAction)
        class NoInternetError(retryAction: () -> Unit) : Error(retryAction)
    }

    sealed class Action{
        class PlayVideo(val url: String): Action()
    }
}