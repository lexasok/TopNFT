package net.ozero.android.topnft.core


data class Res<T>(val status: Status, val data: T? = null, val error: Throwable? = null)