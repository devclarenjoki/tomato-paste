package com.codility

import androidx.databinding.ObservableField
import androidx.databinding.ObservableBoolean
import com.codility.scheduler.SchedulerFacade
import com.codility.ui.navigation.SubmitEmailRouter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.Single

class SignUpToNewsletterViewModel(
    private val submitEmailUseCase: SubmitEmailUseCase,
    private val router: SubmitEmailRouter,
    private val schedulerFacade: SchedulerFacade
) {
    // UI state
    val buttonEnabled = ObservableBoolean(false)
    val input = ObservableField<String>()
    val successMessage = ObservableField<String>()
    val errorMessage = ObservableField<String>()
    val showLoading = ObservableBoolean(false)

    private val disposables = CompositeDisposable()

    init {
        // Set up input validation
        input.addOnPropertyChangedCallback(object : ObservableField.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: ObservableField<*>, propertyId: Int) {
                validateEmail()
            }
        })
    }

    private fun validateEmail() {
        val email = input.get() ?: ""
        val isValid = isEmailValid(email)
        buttonEnabled.set(isValid)
        
        // Clear messages when input changes
        if (isValid) {
            errorMessage.set(null)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.isBlank()) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    fun onSubmitClick() {
        val email = input.get() ?: return
        
        if (!isEmailValid(email)) {
            errorMessage.set("Invalid email address")
            return
        }

        // Show loading indicator
        showLoading.set(true)
        buttonEnabled.set(false)
        
        // Clear previous messages
        successMessage.set(null)
        errorMessage.set(null)

        // Submit email
        val disposable = submitEmailUseCase.submit(email)
            .subscribeOn(schedulerFacade.io())
            .observeOn(schedulerFacade.ui())
            .subscribe(
                { result ->
                    // Hide loading
                    showLoading.set(false)
                    buttonEnabled.set(true)
                    
                    if (result.success) {
                        successMessage.set(result.message ?: "Successfully subscribed to newsletter!")
                        // Clear input after successful submission
                        input.set("")
                    } else {
                        errorMessage.set(result.message ?: "Failed to subscribe. Please try again.")
                    }
                },
                { error ->
                    // Hide loading
                    showLoading.set(false)
                    buttonEnabled.set(true)
                    errorMessage.set("An error occurred: ${error.message}")
                }
            )
        
        disposables.add(disposable)
    }

    fun onCleared() {
        disposables.clear()
    }
}