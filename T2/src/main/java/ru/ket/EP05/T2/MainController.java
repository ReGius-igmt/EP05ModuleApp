package ru.ket.EP05.T2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.controller.Controller;

public class MainController implements Controller {

    @FXML
    private GridPane grid;

    @FXML
    private TextField sizeField;

    private TextField[][] nodes;

    @FXML
    void onResult(ActionEvent event) {
        int l = nodes.length;
        double[][] values = new double[l][6];
        try {
            for (int i = l-1; i >= 0; i--) {
                values[i][0] = Double.parseDouble(nodes[i][0].getText());
                values[i][1] = values[i][0] * (values[i][0] - 4) * (values[i][0] - 7);
                if(i < l-1){
                    for (int j = 0; j < 3; j++) {
                        values[i][2 + j] = values[i+1][1 + j] - values[i][1 + j];
                    }
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Ошибка " + e.getLocalizedMessage());
            e.printStackTrace();
            return;
        }


        for (int i = 0; i < l; i++) {
            for (int j = 0; j < 5; j++) {
                if(i < l-1 || j < 2)nodes[i][j].setText(String.format("%.2f", values[i][j]));
            }
        }
    }

    private void generate(int size){
        if(size < 1 || size > 10)return;
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        for (int i = 0; i < size; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints());
            grid.getRowConstraints().add(new RowConstraints());
        }
        String[] titles = new String[]{"i", "xi", "yi", "Δyi", "Δ^2yi", "Δ^3yi"};
        nodes = new TextField[size][6];
        for (int i = 0; i < 6; i++) {
            Label text = new Label(titles[i]);
            if(i > 0)controlStyle(text, new BorderWidths(1,1,1,0));
            else controlStyle(text, new BorderWidths(1,1,1,1));
            grid.add(text, i, 0);
        }
        for (int i = 0; i < size; i++) {
            Label text = new Label(i+"");
            controlStyle(text, new BorderWidths(0,1,1,1));
            grid.add(text, 0, i+1);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 5; j++) {
                nodes[i][j] = new TextField("0");
                controlStyle(nodes[i][j], new BorderWidths(0,1,1,0));
                if(j > 0){
                    nodes[i][j].setFocusTraversable(false);
                    nodes[i][j].setMouseTransparent(true);
                }
            }
        }

        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < 5; j++) {
                grid.add(nodes[i][j], j+1, i+1);
            }
        }
    }

    private void controlStyle(TextField f, BorderWidths bw){
        f.setAlignment(Pos.CENTER);
        controlStyle((Control)f, bw);
    }

    private void controlStyle(Labeled f, BorderWidths bw){
        f.setAlignment(Pos.CENTER);
        controlStyle((Control)f, bw);
    }

    private void controlStyle(Control control, BorderWidths bw){
        control.setPrefSize(70, 30);
        control.setMinSize(70, 30);
        control.setMaxSize(70, 30);
        control.setBorder(new Border(new BorderStroke(Paint.valueOf("#000"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, bw)));
    }

    @Override
    public void init(SimpleViewHandler vh) {
        grid.setGridLinesVisible(true);
        sizeField.setOnAction(event -> generate(Integer.parseInt(sizeField.getText())));
        sizeField.setText("6");
        generate(6);
    }
}
