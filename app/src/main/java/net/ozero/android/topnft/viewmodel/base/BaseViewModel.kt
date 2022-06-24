package net.ozero.android.topnft.viewmodel.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.ozero.android.topnft.uscase.base.UseCaseExecutor

abstract class BaseViewModel : ViewModel() {

    protected val executor by lazy { UseCaseExecutor { throwableError.value = it } }

    val throwableError = MutableLiveData<Throwable>()

    abstract fun onViewInitialized()

    fun onViewDestroyed() {
        executor.onViewDestroyed()
    }

    override fun onCleared() {
        super.onCleared()
        executor.onViewModelCleared()
    }
}