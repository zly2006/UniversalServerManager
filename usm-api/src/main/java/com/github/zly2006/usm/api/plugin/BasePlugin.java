package com.github.zly2006.usm.api.plugin;

import com.github.zly2006.usm.api.event.Event;
import com.github.zly2006.usm.api.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class BasePlugin implements Plugin {
    List<Listener> listeners = new ArrayList<>();
    PluginDescription description;

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void addListener(Plugin plugin, Listener listener) {

    }

    @Override
    public void removeListener(Plugin plugin, Listener listener) {

    }

    @Override
    public void callEvent(Event<?> event) {

    }

    public List<Listener> getListeners() {
        return this.listeners;
    }

    public PluginDescription getDescription() {
        return this.description;
    }

    public void setDescription(PluginDescription description) {
        this.description = description;
    }
}
