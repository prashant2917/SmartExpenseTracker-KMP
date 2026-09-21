package com.swarajya.smartexpensetracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform