package com.github.zly2006.usm.api;

import com.github.zly2006.usm.api.event.EventSource;
import com.github.zly2006.usm.api.instance.InstanceFolder;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface PanelServer extends EventSource {
    boolean running();
    AccountService accounts();
    Collection<InstanceFolder> instanceFolders();
    InstanceFolder createInstanceFolder(@NotNull String name);
    void deleteInstanceFolder(@NotNull String name);
    InstanceFolder gwtInstanceFolder(@NotNull String name);
}
