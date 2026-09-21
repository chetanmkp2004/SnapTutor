package com.example.snaptutor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform