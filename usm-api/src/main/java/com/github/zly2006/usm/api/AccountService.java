package com.github.zly2006.usm.api;

import com.github.zly2006.usm.api.exception.UserExistedException;
import com.github.zly2006.usm.api.user.User;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a user account service.
 */
public interface AccountService {
    Collection<? extends User> users();
    User createUser(@NotNull String name) throws UserExistedException;
    User getUser(@NotNull UUID id);
    void deleteUser(@NotNull UUID id);
    Optional<? extends User> findUser(@NotNull String username);
}
