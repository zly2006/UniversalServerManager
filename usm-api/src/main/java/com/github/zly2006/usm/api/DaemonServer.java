package com.github.zly2006.usm.api;

import com.github.zly2006.usm.api.instance.Instance;

import java.util.Collection;

public interface DaemonServer {
    boolean running();
    Collection<Instance> instances();
}
