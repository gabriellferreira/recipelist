package br.com.gabriellferreira.recipelist.domain.model

interface Retryable {
    fun retry()
}