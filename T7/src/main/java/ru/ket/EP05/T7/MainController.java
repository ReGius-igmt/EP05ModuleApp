package ru.ket.EP05.T7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.controller.Controller;

import java.util.LinkedList;
import java.util.List;

public class MainController implements Controller {

    @FXML
    private TextField expressionField;

    @FXML
    private Text result;

    @FXML
    private TextField staplesField;

    @FXML
    void onResult(ActionEvent event) {
        try {
            String staple = staplesField.getText().replace(" ", "");
            String res = expressionField.getText().replace(" ", "").replace(staple, "(" + staple + ")");
            result.setText(res);
        } catch (Exception e) {
            e.printStackTrace();
            result.setText("Ошибка " + e.getLocalizedMessage());
        }
    }

    @Override
    public void init(SimpleViewHandler vh) {
        expressionField.setText("a + b / c + d - cos(x) + f + p * c + d");
        staplesField.setText("c + d");
    }
}
