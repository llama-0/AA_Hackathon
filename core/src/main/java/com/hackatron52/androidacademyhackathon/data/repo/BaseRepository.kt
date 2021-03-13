package com.hackatron52.androidacademyhackathon.data.repo

import com.hackatron52.androidacademyhackathon.domain.Lce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


open class BaseRepository {

    protected suspend fun <T> safeApiCall(
        call: suspend () -> T
    ): Flow<Lce<T>> =
        flow {
            emit(Lce(call.invoke()))
        }
            .catch {
                emit(Lce.error(it))
            }
            .flowOn(Dispatchers.IO)
}