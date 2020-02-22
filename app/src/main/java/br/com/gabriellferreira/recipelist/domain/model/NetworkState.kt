package br.com.gabriellferreira.recipelist.domain.model

class NetworkState(
    val state: State,
    val retryable: Retryable? = null
){
    enum class State{
        IN_PROGRESS, LOADED, ERROR
    }
}