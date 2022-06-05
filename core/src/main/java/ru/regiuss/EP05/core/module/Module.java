package ru.regiuss.EP05.core.module;

import ru.regiuss.EP05.core.SimpleViewHandler;

import java.io.File;

public interface Module {
    String getTitle();
    ModuleInfo getInfo();
    void setInfo(ModuleInfo info);
    void onLoad();
    void onSelect(SimpleViewHandler vh);
    void onUnselect();
    void onExit();
    File getWorkDirectory();
    void setWorkDirectory(File f);
}
