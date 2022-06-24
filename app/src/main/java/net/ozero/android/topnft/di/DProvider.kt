package net.ozero.android.topnft.di

import net.ozero.android.topnft.data.MoralisSourceImpl
import net.ozero.android.topnft.entity.source.MoralisSource
import net.ozero.android.topnft.network.moralis.MoralisApiService
import net.ozero.android.topnft.network.moralis.RetrofitBuilder
import net.ozero.android.topnft.uscase.main.GetNFTsUseCase
import net.ozero.android.topnft.uscase.main.LoadMoreNFTsByQueryUseCase

object DProvider {

    private val states = mutableMapOf<Pair<String, String?>, Any>()

    fun provide(className: String, tag: String? = null): Any = states[Pair(className, tag)] ?: initState(className, getState(className, tag), tag)

    private fun initState(className: String, state: Any, tag: String? = null): Any {
        states[Pair(className, tag)] = state
        return state
    }

    private fun getState(className: String, tag: String? = null): Any = when (className) {

        MoralisApiService::class.java.name -> RetrofitBuilder.moralisApiService
        MoralisSource::class.java.name -> MoralisSourceImpl()
        GetNFTsUseCase::class.java.name -> GetNFTsUseCase.Impl()
        LoadMoreNFTsByQueryUseCase::class.java.name -> LoadMoreNFTsByQueryUseCase.Impl()



        else -> throw Throwable("$ERROR_DEPENDENCE_NOT_EXIST: $className")
    }
}

const val ERROR_DEPENDENCE_NOT_EXIST = "Dependence not exist"

inline fun <reified T> provide(tag: String? = null): T = DProvider.provide(T::class.java.name, tag) as T