package ru.regiuss.EP05.core.module;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModuleInfo {
    @JsonProperty("name")
    private String name;
    @JsonProperty("version")
    private String version;
    @JsonProperty("main")
    private String main;

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getMain() {
        return main;
    }
}
