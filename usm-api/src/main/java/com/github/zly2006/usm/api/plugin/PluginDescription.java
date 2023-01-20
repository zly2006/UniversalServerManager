package com.github.zly2006.usm.api.plugin;

import com.github.zly2006.usm.api.Version;

import java.util.List;

public class PluginDescription {
    String id;
    String name;
    Version version;
    String description;
    List<Author> authors;
    Class<Plugin> mainClass;
    public static class Author {
        String name;
        String email;
        String url;
    }
}
