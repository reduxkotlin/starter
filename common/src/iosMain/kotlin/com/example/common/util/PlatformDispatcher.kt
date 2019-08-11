package com.example.common.util

/**
 * A dispatchers for coroutines.  This will likely be removed once multithreaded coroutines
 * for Kotlin Native are supported.  For now each platform has an implementation.
 */
actual object PlatformDispatcher : CoroutineDispatcher()