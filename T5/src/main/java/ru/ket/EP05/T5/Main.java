package ru.ket.EP05.T5;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.regiuss.EP05.core.SimpleViewHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        App.launch(App.class, args);
//        try {
//            FileOutputStream os = new FileOutputStream("./asdas.txt");
//            ThreadLocalRandom rnd = ThreadLocalRandom.current();
//            int max = 0;
//            int c = 0;
//            StringBuilder phone = new StringBuilder();
//            for (int i = 0; i < 1000; i++) {
//                StringBuilder sb = new StringBuilder();
//                if(c >= max){
//                    max = rnd.nextInt(10, 90);
//                    c = 0;
//                    phone = new StringBuilder();
//                    phone.append(rnd.nextInt(9));
//                    for (int j = 0; j < 3; j++) {
//                        phone.append(rnd.nextInt(100, 999));
//                    }
//                }
//                sb.append(phone);
//                sb.append(";");
//                sb.append(rnd.nextInt(193));
//                sb.append(";");
//                sb.append(rnd.nextInt(100));
//                sb.append("\n");
//                os.write(sb.toString().getBytes(StandardCharsets.UTF_8));
//                c++;
//            }
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static class App extends Application{

        @Override
        public void start(Stage stage) throws Exception {
            T5 t5 = new T5();
            t5.setWorkDirectory(new File("./T5/"));
            SimpleViewHandler vh = new SimpleViewHandler(t5);
            vh.init(stage, "/view/main.fxml", new MainController());
            vh.show();
        }
    }
}
