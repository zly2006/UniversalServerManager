package com.github.zly2006.usm.api;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public interface AbstractCommand {
    record CommandResult(int code, String message) {
        public final static CommandResult SUCCESS = new CommandResult(0, "success");
    }
    void execute(@Nullable String[] args);
    CompletableFuture<CommandResult> executeFuture(@Nullable String[] args);
}
