package net.ozero.android.topnft.data.moralis

import com.google.gson.annotations.SerializedName
import net.ozero.android.topnft.network.moralis.KEY_RESULT

data class NFTResponse(
    @SerializedName(KEY_RESULT)
    val nfts: List<NFTDataModel>
)