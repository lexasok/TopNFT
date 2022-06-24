package net.ozero.android.topnft.uscase.base

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import net.ozero.android.topnft.core.Status

class UseCaseExecutor(private val onThrow: (Throwable) -> Unit) {

    private val viewJob = SupervisorJob()
    private val viewModelJob = SupervisorJob()

    fun <TParams, TResult> execute(
        useCase: FlowUseCase<TParams, TResult>,
        params: TParams? = null,
        onSuccess: (TResult) -> Unit = {},
        onLoading: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        CoroutineScope(getContext(useCase.scopeType(), Dispatchers.Main)).launch {
            useCase
                .execute(params)
                .catch {
                    it.printStackTrace()
                    onThrow(it)
                }.collect {
                    when (it.status) {
                        Status.SUCCESS -> onSuccess(it.data!!)
                        Status.ERROR -> onError(it.error!!)
                        Status.LOADING -> onLoading()
                        Status.NONE -> {}
                    }
                }
        }
    }

    fun <TParams, TResult> execute(
        useCase: DeferedUseCase<TParams, TResult>,
        params: TParams? = null,
        onSuccess: (TResult) -> Unit = {},
        onLoading: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {

        CoroutineScope(getContext(useCase.scopeType(), Dispatchers.Main)).launch {
            try {
                useCase.execute(params).await().run {
                    when (status) {
                        Status.SUCCESS -> onSuccess(data!!)
                        Status.ERROR -> onError(error!!)
                        Status.LOADING -> onLoading()
                        Status.NONE -> {}
                    }
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                onThrow(t)
            }
        }
    }

    private fun getContext(scopeType: ScopeType, dispatcher: CoroutineDispatcher) = when (scopeType) {
        ScopeType.APP -> { CoroutineScope(dispatcher) }
        ScopeType.VIEW_MODEL -> CoroutineScope(dispatcher + viewModelJob)
        ScopeType.VIEW -> CoroutineScope(dispatcher + viewJob)
        ScopeType.GLOBAL -> TODO("Not yet implemented")
    }.coroutineContext


    fun onViewDestroyed() {
        viewJob.cancel()
    }

    fun onViewModelCleared() {
        viewModelJob.cancel()
    }
}