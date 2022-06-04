package ru.ket.EP05.T1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Controller {

    @FXML
    private TextField aField;

    @FXML
    private Text res;

    @FXML
    private TextField xField;

    @Override
    public void init(SimpleViewHandler vh) {

        ChangeListener<String> onUpdate = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                try {
                    double a = Double.parseDouble(aField.getText());
                    double x = Double.parseDouble(xField.getText());
                    if(a > x){
                        res.setText(String.format("%.2f", Math.max(Math.max(Math.abs(a+x), Math.pow(x,3) * Math.sqrt(Math.pow(x, 5+a))), 1.0/Math.tan(x))));
                    }else if(a < x){
                        res.setText(String.format("%.2f", Math.pow(Math.sin(x), a)));
                    }else{
                        res.setText(String.format("%.2f", Math.pow(Math.E, a + x)));
                    }
                } catch (Exception ignored) {
                    res.setText("ошибка");
                }
            }
        };

        xField.textProperty().addListener(onUpdate);
        aField.textProperty().addListener(onUpdate);
    }
}
