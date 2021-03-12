package com.hackatron52.androidacademyhackathon.presentation.model

class TimeInterval(val timeFrom: String, val timeTo: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TimeInterval

        if (timeFrom != other.timeFrom) return false
        if (timeTo != other.timeTo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = timeFrom.hashCode()
        result = 31 * result + timeTo.hashCode()
        return result
    }
}