package com.github.wolpl.events

import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Event<T> {
    private val listeners = mutableListOf<(T) -> Unit>()
    private val continuations = mutableListOf<Continuation<T>>()

    /**
     * Registers a listener to be notified when the event fires.
     * @param listener The listener to be registered.
     * @since 1.0
     */
    fun addListener(listener: (T) -> Unit) {
        listeners.add(listener)
    }

    /**
     * Removes an already registered listener.
     * @param listener The listener to be removed.
     * @since 1.0
     */
    fun removeListener(listener: (T) -> Unit) {
        listeners.remove(listener)
    }

    /**
     * Registers a listener to be notified when the event fires.
     * @param listener The listener to be registered.
     * @since 1.0
     */
    operator fun plusAssign(listener: (T) -> Unit) = addListener(listener)

    /**
     * Removes an already registered listener.
     * @param listener The listener to be removed.
     * @since 1.0
     */
    operator fun minusAssign(listener: (T) -> Unit) = removeListener(listener)

    /**
     * Fires the event. All registered listeners are then invoked.
     * @since 1.0
     */
    operator fun invoke(data: T) {
        listeners.forEach { it(data) }
        val continuationsCopy = continuations.toTypedArray()
        continuations.clear()
        continuationsCopy.forEach { it.resume(data) }
    }

    /**
     * Suspends the current coroutine until the event fires.
     * @since 1.2
     */
    suspend fun awaitFire() = suspendCoroutine<T> {
        continuations.add(it)
    }
}

/**
 * Fires the event. All registered listeners are then invoked.
 * @since 1.0
 */
operator fun Event<Unit>.invoke() {
    invoke(Unit)
}