package net.ozero.android.topnft.uscase.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import net.ozero.android.topnft.core.Res

sealed class UseCase<TParams, TResult> {

    abstract fun pool(): Pool

    abstract fun scopeType(): ScopeType

    abstract suspend fun execute(params: TParams?): TResult

    private fun getDispatcher() = when(pool()) {
        Pool.IO -> Dispatchers.IO
        Pool.COMPUTATION -> Dispatchers.Default
        Pool.MAIN -> Dispatchers.Main
    }

    protected fun getScope() = CoroutineScope(getDispatcher())
}

abstract class FlowUseCase<TParams, TResult> : UseCase<TParams, Flow<Res<TResult>>>()

abstract class DeferedUseCase<TParams, TResult> : UseCase<TParams, Deferred<Res<TResult>>>()