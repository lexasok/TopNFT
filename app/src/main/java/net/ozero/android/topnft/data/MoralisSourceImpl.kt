package net.ozero.android.topnft.data

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import net.ozero.android.topnft.core.Res
import net.ozero.android.topnft.core.Status
import net.ozero.android.topnft.core.dto.NFT
import net.ozero.android.topnft.di.provide
import net.ozero.android.topnft.entity.log
import net.ozero.android.topnft.entity.source.MoralisSource
import net.ozero.android.topnft.network.moralis.*

// TODO use facade, remote and local sources
@FlowPreview
class MoralisSourceImpl : MoralisSource {

    private val service by lazy { provide<MoralisApiService>() }

    // Cash
    private val nfts = MutableStateFlow<Res<List<NFT>>>(Res(Status.NONE))

    // TODO add error parsing
    // TODO change pagination
    override suspend fun loadNFTsByQuery(query: String, limit: Int) {
        nfts.update { Res(Status.LOADING) }
        nfts.update {
            val result = (nfts.value.data ?: mutableListOf()).plus(service.search(mapOf(
                KEY_QUERY to query,
                KEY_LIMIT to limit
            )).nfts.map { it.toDTO() })
            log("NFTs: $result")
            Res(Status.SUCCESS, result)
        }
    }

    override suspend fun clearLocalNFTs() {
        nfts.update { Res(Status.NONE) }
    }

    override suspend fun getNNFTs(): Flow<Res<List<NFT>>> = nfts

}