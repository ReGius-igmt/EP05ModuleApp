package ru.regiuss.EP05.core.selectable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.controller.Controller;

import java.io.IOException;
import java.net.URL;


public class SingleSelectable implements Selectable{

    private Node node;
    private final URL resource;
    private final Controller controller;

    public SingleSelectable(URL resource, Controller controller){
        this.resource = resource;
        this.controller = controller;
    }

    @Override
    public void select(SimpleViewHandler vh) {
        if(node == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setController(controller);
            loader.setLocation(resource);
            try {
                node = loader.load();
                controller.init(vh);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        vh.openPage(node);
    }
}
