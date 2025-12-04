package com.codility.mvp

import io.reactivex.disposables.CompositeDisposable


class Presenter(
    view: ListContract.View,
     elementsProvider: ElementsProvider,
     schedulerFacade: SchedulerFacade
) {
     val disposables = CompositeDisposable()

    init {
        disposables.add(
            elementsProvider.loadElements()
                .subscribeOn(schedulerFacade.background)
                .observeOn(schedulerFacade.main)
                .doOnSubscribe { view.showLoading() }
                .subscribe(
                    { elements ->
                        if (elements.isEmpty()) {
                            // Handle empty list - using existing methods
                            view.showEmptyList()
                        } else {
                            // Show elements - using existing methods
                            view.showList(elements)
                        }
                    },
                    { _ -> 
                        // Show error without parameters
                        view.showError() 
                    }
                )
        )
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