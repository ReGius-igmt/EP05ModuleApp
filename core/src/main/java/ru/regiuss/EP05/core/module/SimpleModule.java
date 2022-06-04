package ru.regiuss.EP05.core.module;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.controller.Controller;

import java.io.File;

public abstract class SimpleModule<T extends Controller> implements Module{

    protected ModuleInfo info;
    protected SimpleViewHandler vh;
    protected File workDirectory;
    protected Class<T> typeParameterClass;

    public SimpleModule(Class<T> typeParameterClass){
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnselect() {

    }

    @Override
    public void onSelect(ObjectProperty<Node> page, ObjectProperty<Node> modal) {
        if(vh == null){
            vh = new SimpleViewHandler(this);
            vh.init();
        }
        vh.bind(page, modal);
        try {
            vh.openPage(typeParameterClass.getResource("/view/main.fxml"), typeParameterClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onExit() {

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
