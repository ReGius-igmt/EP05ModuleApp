package ru.regiuss.EP05.core.module;

import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.selectable.Selectable;
import ru.regiuss.EP05.core.selectable.SingleSelectable;

import java.io.File;

public abstract class SimpleModule implements Module{

    protected ModuleInfo info;
    protected File workDirectory;
    protected Selectable selectable;

    @Override
    public void onLoad() {}

    @Override
    public void onUnselect() {}

    @Override
    public void onExit() {}

    @Override
    public void onSelect(SimpleViewHandler vh) {
        selectable.select(vh);
    }

    @Override
    public ModuleInfo getInfo() {
        return info;
    }

    @Override
    public void setInfo(ModuleInfo info) {
        this.info = info;
    }

    @Override
    public File getWorkDirectory() {
        return workDirectory;
    }

    @Override
    public void setWorkDirectory(File f) {
        this.workDirectory = f;
    }
}
