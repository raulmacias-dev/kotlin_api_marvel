package com.raul.macias.wikiheroes.api

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}

//Manejar el estado de la respuesta
data class Resource<T> private constructor(val status: Status, val data: T?, val error: Throwable?) {
    companion object {

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Throwable?): Resource<T> {
            return Resource(Status.ERROR, null, exception)
        }
    }
}