package com.github.wolpl.events

class Event<T> {
    private val listeners = mutableListOf<(T) -> Unit>()

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
    }
}

/**
 * Fires the event. All registered listeners are then invoked.
 * @since 1.0
 */
operator fun Event<Unit>.invoke() {
    invoke(Unit)
}