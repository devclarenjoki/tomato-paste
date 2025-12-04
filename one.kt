package com.codility.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class Presenter(
    private val view: ListContract.View,
    private val elementsProvider: ElementsProvider,
    private val schedulerFacade: SchedulerFacade
) {
    private val disposables = CompositeDisposable()

    init {
        val disposable = elementsProvider.downloadElements()
            .subscribeOn(schedulerFacade.io())
            .observeOn(schedulerFacade.mainThread())
            .doOnSubscribe { view.showLoading() }
            .subscribe(
                { elements ->
                    when {
                        elements.isEmpty() -> view.showEmpty()
                        else -> view.showElements(elements)
                    }
                },
                { _ -> view.showError() }
            )
        disposables.add(disposable)
    }

    fun onDestroy() {
        disposables.clear()
    }
}

// Required interfaces (these should match what's expected by the platform)
interface ListContract {
    interface View {
        fun showLoading()
        fun showElements(elements: List<String>)
        fun showError()
        fun showEmpty()
    }
}

interface ElementsProvider {
    fun downloadElements(): Single<List<String>>
}

interface SchedulerFacade {
    fun io(): io.reactivex.Scheduler
    fun mainThread(): io.reactivex.Scheduler
}