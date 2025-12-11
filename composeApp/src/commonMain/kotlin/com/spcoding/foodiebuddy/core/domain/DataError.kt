package com.spcoding.foodiebuddy.core.domain

sealed interface DataError : Error {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        SERVER,
        SERIALIZATION,
        NO_INTERNET,
        TOO_MANY_REQUESTS,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN
    }
}