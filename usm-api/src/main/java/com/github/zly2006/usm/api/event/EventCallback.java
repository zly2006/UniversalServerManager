package com.github.zly2006.usm.api.event;

import com.github.zly2006.usm.api.Result;

public interface EventCallback<T> {
    Result call(Event<T> event);
}
