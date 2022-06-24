package net.ozero.android.topnft.network.moralis

import net.ozero.android.topnft.BuildConfig.MORALIS_API_KEY
import net.ozero.android.topnft.data.moralis.NFTResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface MoralisApiService {

    // TODO make headers in retrofit
    @Headers("Accept: application/json", "$KEY_API_KEY: $MORALIS_API_KEY")
    @GET("nft/search")
    suspend fun search(@QueryMap params: Map<String, @JvmSuppressWildcards Any>): NFTResponse
}