package net.ozero.android.topnft.uscase.main

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import net.ozero.android.topnft.core.Res
import net.ozero.android.topnft.core.dto.NFT
import net.ozero.android.topnft.di.provide
import net.ozero.android.topnft.entity.source.MoralisSource
import net.ozero.android.topnft.uscase.base.FlowUseCase
import net.ozero.android.topnft.uscase.base.Pool
import net.ozero.android.topnft.uscase.base.ScopeType

abstract class GetNFTsUseCase : FlowUseCase<Unit, List<NFT>>() {

    @FlowPreview
    class Impl : GetNFTsUseCase() {

        private val moralisSource by lazy { provide<MoralisSource>() }

        override fun pool(): Pool = Pool.IO

        override fun scopeType(): ScopeType = ScopeType.APP

        override suspend fun execute(params: Unit?): Flow<Res<List<NFT>>> = moralisSource.getNNFTs().debounce(TIME_OUT)
    }

    companion object {

        const val TIME_OUT = 3000L
    }
}