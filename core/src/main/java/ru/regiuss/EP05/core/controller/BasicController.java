package ru.regiuss.EP05.core.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.module.Module;
import ru.regiuss.EP05.core.module.ModuleInfo;
import ru.regiuss.EP05.core.module.ModuleManager;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Comparator;
import java.util.Properties;

public class BasicController implements Controller{

    @FXML
    private BorderPane modal;

    @FXML
    private VBox modelsList;

    @FXML
    private BorderPane page;

    @FXML
    private AnchorPane root;

    protected SimpleViewHandler vh;

    @Override
    public void init(SimpleViewHandler vh) {

        this.vh = vh;
        this.modal.setVisible(false);
        this.modal.setManaged(false);
        vh.bind(page.centerProperty(), modal.centerProperty());
        ToggleGroup navigationGroup = new ToggleGroup();

        ModuleManager.getInstance().loadModules(event -> {
            ModuleManager.getInstance().getModules().stream().sorted(Comparator.comparing(Module::getTitle)).forEach(module -> {
                ToggleButton btn = new ToggleButton(module.getTitle());
                btn.setAlignment(Pos.CENTER_LEFT);
                btn.setToggleGroup(navigationGroup);
                navigationGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
                    if(btn.equals(t1)){
                        vh.setModule(module);
                        module.onSelect(vh);
                    } else if(btn.equals(toggle)){
                        module.onUnselect();
                        vh.setModule(null);
                    }
                    if(t1 == null)vh.openPage(null);
                });
                modelsList.getChildren().add(btn);
            });
        });
    }
}
