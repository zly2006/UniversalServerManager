package com.github.zly2006.usm.api.instance;

import com.github.zly2006.usm.api.AbstractCommand;
import com.github.zly2006.usm.api.event.EventSource;
import com.github.zly2006.usm.api.event.Listener;
import com.github.zly2006.usm.api.Permission;
import com.github.zly2006.usm.api.text.Text;
import com.github.zly2006.usm.api.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.UUID;

public interface Instance extends EventSource {
    boolean running();
    void start();
    void stop();
    @NotNull
    UUID id();
    @NotNull
    Path root();
    @NotNull
    Text getName();
    @NotNull
    AbstractCommand startCommand();
    @NotNull
    AbstractCommand stopCommand();
    @Nullable
    InstanceFolder folder();
    default boolean checkPermission(User user, String permission) {
        return checkPermission(user, Permission.valueOf(permission));
    }
    boolean checkPermission(User user, Permission permission);
    default void setPermission(User user, String permission, boolean value) {
        setPermission(user, Permission.valueOf(permission), value);
    }
    void setPermission(User user, Permission permission, boolean value);
}
