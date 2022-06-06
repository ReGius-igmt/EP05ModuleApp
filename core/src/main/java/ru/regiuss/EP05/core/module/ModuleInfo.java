package ru.regiuss.EP05.core.module;

import java.util.Properties;

public class ModuleInfo {
    private String name;
    private String version;
    private String main;

    public ModuleInfo(){}

    public ModuleInfo(Properties properties){
        name = properties.getProperty("name");
        version = properties.getProperty("version");
        main = properties.getProperty("main");
    }

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
