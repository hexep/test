package com.ihexep.domain.common

sealed class Resource<out T> {
    data class Loaded<T>(val data: T): Resource<T>()
    data class Error(val errorMessage: String): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}