package net.ozero.android.topnft.data.moralis

import com.google.gson.annotations.SerializedName
import net.ozero.android.topnft.core.dto.NFT
import net.ozero.android.topnft.network.moralis.KEY_METADATA
import net.ozero.android.topnft.network.moralis.KEY_TOKEN_ID

data class NFTDataModel(
    @SerializedName(KEY_TOKEN_ID)
    val id: String,
    @SerializedName(KEY_METADATA)
    val metadata: String,
) {
    fun toDTO() = NFT(id, "", "")
}