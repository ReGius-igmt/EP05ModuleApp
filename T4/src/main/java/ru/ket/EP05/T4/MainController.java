package ru.ket.EP05.T4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.controller.Controller;

import java.util.concurrent.ThreadLocalRandom;

public class MainController implements Controller {

    @FXML
    private GridPane grid;

    @FXML
    private GridPane resGrid;

    @FXML
    private TextField scalar;

    @FXML
    private TextField size;

    private TextField[][] nodes;

    @FXML
    void onGenerate(ActionEvent event) {
        try {
            int n = Integer.parseInt(size.getText());
            if(n < 1)throw new RuntimeException("Размер не может быть меньше 1");
            if(n > 10)throw new RuntimeException("Размер не может быть больше 10");
            resGrid.setVisible(false);
            resGrid.setManaged(false);
            generate(n);
        } catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Неверный формат числа").show();
        } catch (RuntimeException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Неизвестная ошибка. " + e.getMessage()).show();
        }
    }

    private void generate(int n){
        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();
        grid.getChildren().clear();
        for (int i = 0; i < n; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints());
        }
        for (int i = 0; i < n; i++) {
            grid.getRowConstraints().add(new RowConstraints());
        }
        if(nodes == null || nodes.length != n)nodes = new TextField[n][n];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                TextField tf = new TextField(Integer.toString(random.nextInt(100)));
                tf.setMaxSize(50,50);
                tf.setAlignment(Pos.CENTER);
                tf.setPrefSize(50, 50);
                nodes[i][j] = tf;
                grid.add(nodes[i][j], j, i);
            }
        }
    }

    public Integer readValue(TextField field){
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Неверный формат числа").show();
        } catch (RuntimeException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Неизвестная ошибка. " + e.getMessage()).show();
        }
        return null;
    }

    @FXML
    void onResult(ActionEvent event) {

        Integer scalarV = readValue(scalar);
        if(scalarV == null)return;

        int size = nodes.length;
        int[][] values = new int[size][size];
        try {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    values[i][j] = Integer.parseInt(nodes[i][j].getText());
                }
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Неверный формат значений матрицы").show();
            return;
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Ошибка: " + e.getMessage()).show();
            return;
        }

        Matrix m = new Matrix(values);
        values = m.multiply(scalarV);

        resGrid.setVisible(true);
        resGrid.setManaged(true);
        resGrid.getChildren().clear();
        resGrid.getColumnConstraints().clear();
        resGrid.getRowConstraints().clear();

        for (int i = 0; i < size; i++) {
            resGrid.getColumnConstraints().add(new ColumnConstraints());
            resGrid.getRowConstraints().add(new RowConstraints());
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TextField tf = new TextField(Integer.toString(values[i][j]));
                tf.setMaxSize(50,50);
                tf.setAlignment(Pos.CENTER);
                tf.setPrefSize(50, 50);
                resGrid.add(tf, j, i);
            }
        }
    }

    @Override
    public void init(SimpleViewHandler vh) {
        resGrid.setVisible(false);
        resGrid.setManaged(false);
        size.setOnAction(this::onGenerate);
        scalar.setOnAction(this::onResult);
        size.setText("5");
        generate(5);
    }
}
