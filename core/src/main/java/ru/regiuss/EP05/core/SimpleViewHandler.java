package ru.regiuss.EP05.core;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import ru.regiuss.EP05.core.controller.Controller;
import ru.regiuss.EP05.core.module.Module;

import java.io.IOException;
import java.net.URL;

public class SimpleViewHandler {

    protected final String title;
    protected Stage stage;
    protected Module module;
    private final SimpleObjectProperty<Node> page;
    private final SimpleObjectProperty<Node> modal;

    public SimpleViewHandler(String title){
        this.title = title;
        this.page = new SimpleObjectProperty<>();
        this.modal = new SimpleObjectProperty<>();
    }

    public SimpleViewHandler(Module module){
        this(module.getTitle());
        this.module = module;
    }

    public <T extends Controller> void init(Stage stage, String path, T controller){
        this.stage = stage;
        stage.setTitle(title);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setWidth(800);
        stage.setHeight(600);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            loader.setController(controller);
            stage.setScene(new Scene(loader.load()));
            controller.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void init(Stage stage){}

    public void init(){}

    public void bind(ObjectProperty<Node> page, ObjectProperty<Node> modal){
        page.bind(this.page);
        modal.bind(this.modal);
    }

    public <T extends Controller> void openPage(URL location, T controller){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(location);
            loader.setController(controller);
            Node n = loader.load();
            ScrollPane p = new ScrollPane(n);
            p.setFitToHeight(true);
            p.setFitToWidth(true);
            page.setValue(p);
            controller.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        this.stage.show();
    }
}
