package ru.ket.EP05.T2;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.regiuss.EP05.core.SimpleViewHandler;

public class Main{
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

    public static class App extends Application{
        @Override
        public void start(Stage stage) throws Exception {
            SimpleViewHandler vh = new SimpleViewHandler("УП05 ЛР2");
            vh.init(stage, "/view/main.fxml", new MainController());
            vh.show();
        }
    }
}
