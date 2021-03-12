package com.hackatron52.androidacademyhackathon.domain

import android.util.Log

class Lce<T>(val content: T? = null, val loading: Boolean? = null, val error: Throwable? = null) {
    /**
     * @return true when Lce is not in loading or in error state and has non null content
     */
    val isFinishedSuccessfully: Boolean =
        loading != true && content != null && error == null

    init {
        error?.let {
            Log.e("lce.error", "error: $error", it)
        }
    }

    override fun toString(): String =
        "$loading::$content::$error"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Lce<*>

        if (content != other.content) return false
        if (loading != other.loading) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content?.hashCode() ?: 0
        result = 31 * result + (loading?.hashCode() ?: 0)
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }


    inline fun <reified NewType> toLceWithTransformedContent(transform: (T?) -> (NewType?)): Lce<NewType> =
        Lce(transform(this.content), this.loading, this.error)


    companion object {
        fun <T> loading(): Lce<T> = Lce(loading = true)

        @JvmStatic
        fun <T> error(throwable: Throwable): Lce<T> = Lce(error = throwable)

        @JvmStatic
        fun <T> data(data: T?): Lce<T> = Lce(content = data)
    }
}