package net.ozero.android.topnft.entity.source

import kotlinx.coroutines.flow.Flow
import net.ozero.android.topnft.core.Res
import net.ozero.android.topnft.core.dto.NFT


interface MoralisSource {

    suspend fun loadNFTsByQuery(query: String, limit: Int)

    suspend fun clearLocalNFTs()

    suspend fun getNNFTs(): Flow<Res<List<NFT>>>
}