package com.github.zly2006.usm.impl

import com.github.zly2006.usm.api.event.Event
import com.github.zly2006.usm.api.event.EventHandler
import com.github.zly2006.usm.api.event.Listener
import com.github.zly2006.usm.api.plugin.Plugin
import com.github.zly2006.usm.toMultiMap

class ListenerWrapper(
    val owner: Plugin,
    private val listener: Listener
) {
    val callbackMap = listener.javaClass.methods
        .filter { it.isAnnotationPresent(EventHandler::class.java) }
        .map {
            val annotation = it.getAnnotation(EventHandler::class.java)
            if (it.parameters.size != 1) {
                throw IllegalArgumentException("event handler method ${it.name} must have only one parameter")
            }
            val eventType = it.parameters[0].type
            return@map eventType to { event: Event<*> ->
                it.invoke(listener, event)
            }
        }
        .toMultiMap()
        .mapValues { (_, v) ->
            if (v.size == 1) {
                v[0]
            } else {
                { event: Event<*> ->
                    v.forEach { it.invoke(event) }
                }
            }
        }
}
