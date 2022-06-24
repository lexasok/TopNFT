package net.ozero.android.topnft.viewmodel.main

import androidx.lifecycle.MutableLiveData
import net.ozero.android.topnft.core.dto.NFT
import net.ozero.android.topnft.di.provide
import net.ozero.android.topnft.uscase.main.GetNFTsUseCase
import net.ozero.android.topnft.uscase.main.LoadMoreNFTsByQueryUseCase
import net.ozero.android.topnft.viewmodel.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    private val loadMoreNFTsByQueryUseCase by lazy { provide<LoadMoreNFTsByQueryUseCase>() }
    private val getNFTsUseCase by lazy { provide<GetNFTsUseCase>() }

    val nfts = MutableLiveData<List<NFT>>()

    override fun onViewInitialized() {
        executor.execute(
            getNFTsUseCase,
            onSuccess = { nfts.value = it },
            onLoading = {},
            onError = {}
        )
    }

    fun onQueryChanged(query: String) {
        executor.execute(loadMoreNFTsByQueryUseCase, query)
    }

    fun onButtonLoadMore(query: String) {
        executor.execute(loadMoreNFTsByQueryUseCase, query)
    }
}