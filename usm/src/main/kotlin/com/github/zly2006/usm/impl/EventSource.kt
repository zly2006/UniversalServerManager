package com.github.zly2006.usm.impl

import com.github.zly2006.usm.api.event.Event
import com.github.zly2006.usm.api.event.Listener
import com.github.zly2006.usm.api.plugin.Plugin

open class EventSource(

) : com.github.zly2006.usm.api.event.EventSource {
    val _listeners = mutableListOf<ListenerWrapper>()
    override fun getListeners(): MutableList<Listener> {
        TODO("Not yet implemented")
    }

    override fun addListener(plugin: Plugin?, listener: Listener?) {
        TODO("Not yet implemented")
    }

    override fun removeListener(plugin: Plugin?, listener: Listener?) {
        TODO("Not yet implemented")
    }

    override fun callEvent(event: Event<*>) {
        _listeners.forEach {
            it.callbackMap[event.javaClass]?.invoke(event)
        }
    }
}
