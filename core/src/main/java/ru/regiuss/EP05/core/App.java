package ru.regiuss.EP05.core;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.regiuss.EP05.core.controller.BasicController;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SimpleViewHandler vh = new SimpleViewHandler("EP05");
        vh.init(stage, "/view/basic.fxml", new BasicController());
        stage.setWidth(900);
        stage.setMinWidth(900);
        vh.show();
    }
}
