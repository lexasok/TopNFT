package net.ozero.android.topnft.uscase.main

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import net.ozero.android.topnft.core.Res
import net.ozero.android.topnft.core.Status
import net.ozero.android.topnft.di.provide
import net.ozero.android.topnft.entity.source.MoralisSource
import net.ozero.android.topnft.uscase.base.DeferedUseCase
import net.ozero.android.topnft.uscase.base.Pool
import net.ozero.android.topnft.uscase.base.ScopeType

abstract class LoadMoreNFTsByQueryUseCase : DeferedUseCase<String, Unit>() {

    class Impl : LoadMoreNFTsByQueryUseCase() {

        private val moralisSource by lazy { provide<MoralisSource>() }

        private var lastQuery = ""

        override fun pool(): Pool = Pool.IO

        override fun scopeType(): ScopeType = ScopeType.APP

        override suspend fun execute(params: String?): Deferred<Res<Unit>> = getScope().async {
            val result = params?.let {
                if (params.isNullOrEmpty() || params.length < QUERY_MIN_LENGTH) return@async Res(Status.NONE)
                if (params != lastQuery) { moralisSource.clearLocalNFTs() }
                lastQuery = params
                Res(Status.SUCCESS, moralisSource.loadNFTsByQuery(params, PAGE_LIMIT))
            } ?: Res(Status.NONE)
            result
        }

        companion object {

            const val PAGE_LIMIT = 100
            const val QUERY_MIN_LENGTH = 3
        }
    }
}