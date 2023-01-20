package com.github.zly2006.usm.api.exception;

import com.github.zly2006.usm.api.user.User;
import org.jetbrains.annotations.NotNull;

public class UserExistedException extends Exception {
    public UserExistedException(@NotNull User user) {
        super("User " + user.name() + " already existed");
    }
}
