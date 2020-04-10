package com.seweryn.dazncodechallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seweryn.dazncodechallenge.utils.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

abstract class BaseViewModel(private val schedulersProvider: SchedulersProvider) : ViewModel() {

    private val subscriptions = CompositeDisposable()

    fun onDestroy() {
        clearSubscriptions()
    }

    protected fun clearSubscriptions() = subscriptions.clear()

    protected fun <T> load(
        command: Observable<T>,
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        subscriptions.add(
            command.subscribeOn(schedulersProvider.ioScheduler())
                .observeOn(schedulersProvider.uiScheduler(), true)
                .subscribe(
                    { result -> onNext.invoke(result) },
                    { error -> onError.invoke(error) },
                    { onComplete.invoke() })
        )
    }

    protected fun <T> poll(
        command: Observable<T>,
        intervalInSeconds: Long,
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        subscriptions.add(
            command.subscribeOn(schedulersProvider.ioScheduler())
                .observeOn(schedulersProvider.uiScheduler(), true)
                .repeatWhen { completed -> completed.delay(intervalInSeconds, TimeUnit.SECONDS) }
                .subscribe(
                    { result -> onNext.invoke(result) },
                    { error -> onError.invoke(error) },
                    { onComplete.invoke() })
        )
    }

    protected fun <T> sendSingleEvent(liveData: MutableLiveData<T?>, value: T) {
        liveData.value = value
        liveData.value = null
    }
}