package ru.ket.EP05.T3;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.regiuss.EP05.core.SimpleViewHandler;

public class Main {
    public static void main(String[] args) {
        App.launch(App.class, args);
    }

    public static class App extends Application{

        @Override
        public void start(Stage stage) throws Exception {
            SimpleViewHandler vh = new SimpleViewHandler("EP05T3");
            vh.init(stage, "/view/main.fxml", new MainController());
            vh.show();
        }
    }
}
