package com.codility.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Presenter(
    private val view: ListContract.View,
    private val elementsProvider: ElementsProvider,
    private val schedulerFacade: SchedulerFacade
) {
    private val disposables = CompositeDisposable()

    init {
        loadElements()
    }

    private fun loadElements() {
        // Show loading state
        view.showLoading()
        
        val disposable = elementsProvider.getElements()
            .subscribeOn(schedulerFacade.io())
            .observeOn(schedulerFacade.mainThread())
            .subscribe(
                { elements ->
                    if (elements.isEmpty()) {
                        view.showEmpty()
                    } else {
                        view.showElements(elements)
                    }
                },
                { error ->
                    view.showError(error.message ?: "Unknown error")
                }
            )
            
        disposables.add(disposable)
    }

    fun onRefresh() {
        loadElements()
    }

    fun onDestroy() {
        disposables.clear()
    }
}

// Supporting interfaces and classes that would be defined elsewhere:
interface ListContract {
    interface View {
        fun showLoading()
        fun showElements(elements: List<String>)
        fun showError(message: String)
        fun showEmpty()
    }
}

interface ElementsProvider {
    fun getElements(): io.reactivex.Single<List<String>>
}

interface SchedulerFacade {
    fun io(): io.reactivex.Scheduler
    fun mainThread(): io.reactivex.Scheduler
}