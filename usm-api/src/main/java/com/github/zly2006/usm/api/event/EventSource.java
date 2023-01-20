package com.github.zly2006.usm.api.event;

import com.github.zly2006.usm.api.plugin.Plugin;

import java.util.List;

/**
 * Represents a source of events.
 * Maybe a plugin, a user, or a server.
 */
public interface EventSource {
    List<Listener> getListeners();
    void addListener(Plugin plugin, Listener listener);
    void removeListener(Plugin plugin, Listener listener);
    void callEvent(Event<?> event);
}
