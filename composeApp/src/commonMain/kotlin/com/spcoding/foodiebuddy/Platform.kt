package com.spcoding.foodiebuddy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform