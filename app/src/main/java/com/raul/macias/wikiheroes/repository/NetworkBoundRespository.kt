package com.raul.macias.wikiheroes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.raul.macias.wikiheroes.api.ApiResponse
import com.raul.macias.wikiheroes.api.Resource

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
constructor() {
    private val result = MediatorLiveData<Resource<ResultType>>()

    // returns a LiveData that represents the resource
    val asLiveData: LiveData<Resource<ResultType>>
        get() = result



    init {
        result.setValue(Resource.loading(null))
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }


    }


    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = fetchService()
        // we re-attach dbSource as a new source,
        // it will dispatch its latest value quickly
        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            response?.let {
                when(response.isSuccessful) {
                    true -> {
                        response.body?.let {
                            saveFetchData(it)
                            val loaded = loadFromDb()
                            result.addSource(loaded) { newData ->
                                result.removeSource(loaded)
                                setValue(Resource.success(newData))
                            }
                        }
                    }

                    false -> {
                        onFetchFailed()
                        result.addSource(dbSource) {
                            result.setValue(Resource.error(response.error))
                        }
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        result.value = newValue
    }


    // Para guardar el resultado de la respuesta de la API en la base de datos
    @WorkerThread
    protected abstract fun saveFetchData(item: RequestType)

    // Llamada con los datos de la bbdd para decidir si deben obtenerse de la red.
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    // Para obtener los datos en caché de la base de datos
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    // Para crear la llamada a la api
    @MainThread
    protected abstract fun fetchService(): LiveData<ApiResponse<RequestType>>

    // Cuando falla la busqueda
    @MainThread
    protected abstract fun onFetchFailed()
}