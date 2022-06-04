package ru.regiuss.EP05.core.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ru.regiuss.EP05.core.module.Module;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.module.ModuleInfo;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

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

        ObjectMapper om = new ObjectMapper();
        File modulesFolder = new File("./modules");
        modulesFolder.mkdir();
        ObservableList<Module> modules = FXCollections.observableArrayList();
        File[] files = modulesFolder.listFiles();
        if(files != null){
            for(File jar : files){
                System.out.println(jar.getName());
                Module m = loadModule(jar);
                if(m == null)continue;
                modules.add(m);
                m.onLoad();
            }
        }
        for (Module module : modules) {
            ToggleButton btn = new ToggleButton(module.getTitle());
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setToggleGroup(navigationGroup);
            btn.setOnAction(event -> {
                if(btn.isSelected())module.onSelect(page.centerProperty(), modal.centerProperty());
                else module.onUnselect();
            });
            modelsList.getChildren().add(btn);
        }
    }

    private Module loadModule(File f){
        try {
            URLClassLoader child = new URLClassLoader(
                    new URL[] {f.toURI().toURL()},
                    this.getClass().getClassLoader()
            );
            InputStream info = child.getResourceAsStream("module.json");
            if(info == null)throw new RuntimeException("module.json error");
            ModuleInfo moduleInfo = new ObjectMapper().readValue(info, ModuleInfo.class);
            Class<Module> module = (Class<Module>) Class.forName(moduleInfo.getMain(), true, child);
            Module m = module.getDeclaredConstructor().newInstance();
            m.setWorkDirectory(f.getParentFile().toPath().resolve(moduleInfo.getName()).toFile());
            m.setInfo(moduleInfo);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
