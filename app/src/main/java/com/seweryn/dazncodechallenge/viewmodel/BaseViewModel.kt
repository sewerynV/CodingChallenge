package com.seweryn.dazncodechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

open class BaseViewModel : ViewModel() {

    private val subscriptions = CompositeDisposable()

    fun onDestroy() {
        subscriptions.clear()
    }

    protected fun <T>load(command: Observable<T>, onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit = {}) {
        subscriptions.add(
            command.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> onNext.invoke(result) },
                    { error -> onError.invoke(error)},
                    { onComplete.invoke() })
        )
    }

    protected fun <T>poll(command: Observable<T>, intervalInSeconds: Long, onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit = {}) {
        subscriptions.add(
            command.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen { completed -> completed.delay(intervalInSeconds, TimeUnit.SECONDS) }
                .subscribe(
                    { result -> onNext.invoke(result) },
                    { error -> onError.invoke(error)},
                    { onComplete.invoke() })
        )
    }

    protected fun <T>sendSingleEvent(liveData: MutableLiveData<T?>, value: T) {
        liveData.value = value
        liveData.value = null
    }
}