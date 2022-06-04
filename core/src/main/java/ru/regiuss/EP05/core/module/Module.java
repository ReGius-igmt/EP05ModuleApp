package ru.regiuss.EP05.core.module;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.io.File;

public interface Module {
    String getTitle();
    ModuleInfo getInfo();
    void setInfo(ModuleInfo info);
    void onLoad();
    void onSelect(ObjectProperty<Node> page, ObjectProperty<Node> modal);
    void onUnselect();
    void onExit();
    File getWorkDirectory();
    void setWorkDirectory(File f);
}
