package com.github.zly2006.usm.api.event;

public record Event<T>(EventSource source, T data) {
}
