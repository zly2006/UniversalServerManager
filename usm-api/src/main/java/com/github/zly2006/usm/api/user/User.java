package com.github.zly2006.usm.api.user;

import com.github.zly2006.usm.api.event.EventSource;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface User extends EventSource {
    UUID id();
    String name();
    void resetPassword(@NotNull String password);
    boolean admin();
    void setAdmin(boolean admin);
}
