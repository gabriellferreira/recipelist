package br.com.gabriellferreira.recipelist.domain.model


sealed class NetworkState<out T> {

    object InProgress : NetworkState<Nothing>()
    class Loaded<T>(val result: T) : NetworkState<T>()
    class Error(val retryable: Retryable? = null) : NetworkState<Nothing>()
}