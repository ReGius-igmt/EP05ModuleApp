package ru.ket.EP05.T3;

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

    private TextField[][] nodes;

    @FXML
    private TextField size;

    @FXML
    void onGenerate(ActionEvent event) {
        try {
            int n = Integer.parseInt(size.getText());
            if(n < 1)throw new RuntimeException("Размер не может быть меньше 1");
            if(n > 10)throw new RuntimeException("Размер не может быть больше 10");
            generate(n);
        } catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Неверный формат числа").show();
        } catch (RuntimeException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Неизвестная ошибка. " + e.getMessage()).show();
        }
    }

    @FXML
    void onResult(ActionEvent event) {
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

        values = check(values, 0, 0);
        if(values == null){
            new Alert(Alert.AlertType.ERROR, "Невозможная ситуация. Не удалось расположить элементы главной диагонали по возрастанию.").show();
            return;
        }

//        for (int i = 0; i < size; i++) {
//            int min = i;
//            for (int j = i+1; j < size; j++) {
//                if(i > 0 && values[min][i] < values[i-1][i-1])min = j;
//                else if(values[min][i] >= values[j][i] && (i == 0 || values[j][i] >= values[i-1][i-1]))
//                    min = j;
//            }
//            if(i > 0 && values[min][i] < values[i-1][i-1]){
//                new Alert(Alert.AlertType.ERROR, "Невозможная ситуация").show();
//                nodes[i][i].setStyle("-fx-background-color: red;-fx-text-fill: white");
//                break;
//            }
//            if(min != i)swap(values, i, min);
//        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                nodes[i][j].setText(Integer.toString(values[i][j]));
            }
        }
    }

    private int[][] check(int[][] values, int index, int p){
        if(index == values.length - 1){
            if(values[index][index] >= values[index-1][index-1])return values;
            return null;
        }
        for (int i = index; i < values.length; i++) {
            if(values[i][index] >= p && (index == 0 || values[i][index] >= values[index-1][index-1])){
                int[][] v = values.clone();
                if(index != i)swap(v, index, i);
                int[][]res = check(v, index+1, values[i][index]);
                if(res != null)return res;
            }
        }
        return null;
    }

    private void swap(int[][] arr, int i, int j){
        int[] tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private void generate(int n){
        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();
        grid.getChildren().clear();
        for (int i = 0; i < n; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints());
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
                if(i == j){
                    nodes[i][j].setStyle("-fx-background-color: darkgreen; -fx-text-fill: #fff");
                }
                grid.add(nodes[i][j], j, i);
            }
        }
    }

    @Override
    public void init(SimpleViewHandler vh) {
        size.setOnAction(this::onGenerate);
        size.setText("5");
        generate(5);
    }
}
