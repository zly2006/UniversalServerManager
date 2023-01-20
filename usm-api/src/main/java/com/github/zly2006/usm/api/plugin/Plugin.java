package com.github.zly2006.usm.api.plugin;

import com.github.zly2006.usm.api.event.EventSource;

public interface Plugin extends EventSource {
    void onLoad();
    void onEnable();
    void onDisable();
    PluginDescription getDescription();
    void setDescription(PluginDescription description);
}
