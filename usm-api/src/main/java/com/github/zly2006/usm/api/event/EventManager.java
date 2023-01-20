package com.github.zly2006.usm.api.event;

import com.github.zly2006.usm.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface EventManager {
    /**
     * Register a listener for all events.
     *
     * @param plugin The plugin that owns the listener.
     * @param listener The listener to register.
     */
    void registerListener(Plugin plugin, @NotNull Listener listener);

    /**
     * Unregister a listener from this event manager.
     * @param listener The listener to unregister.
     */
    void unregisterListener(@NotNull Listener listener);

    /**
     * Call an event.
     * @param event The event to call.
     * @param <T> The type of the event data.
     */
    <T> void callEvent(@NotNull Event<T> event);
}
