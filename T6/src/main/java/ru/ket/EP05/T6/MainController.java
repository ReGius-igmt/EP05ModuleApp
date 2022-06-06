package ru.ket.EP05.T6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.linear.*;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.controller.Controller;

import java.util.concurrent.ThreadLocalRandom;

public class MainController implements Controller {

    @FXML
    private GridPane grid;

    @FXML
    private GridPane resGrid;

    @FXML
    private TextField size;

    @FXML
    private CheckBox fractionNumCB;

    private TextField[][] nodes;

    @FXML
    void onGenerate(ActionEvent event) {
        try {
            int n = Integer.parseInt(size.getText());
            if(n < 1)throw new RuntimeException("Размер не может быть меньше 1");
            if(n > 10)throw new RuntimeException("Размер не может быть больше 10");
            resGrid.setVisible(false);
            resGrid.setManaged(false);
            generate(n, fractionNumCB.isSelected());
        } catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Неверный формат числа").show();
        } catch (RuntimeException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Неизвестная ошибка. " + e.getMessage()).show();
        }
    }

    private void generate(int n, boolean fraction){
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
                TextField tf = new TextField();
                if(fraction) tf.setText(Double.toString(random.nextInt(10000) / 100.0));
                else tf.setText(Integer.toString(random.nextInt(100)));
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
        int size = nodes.length;
        double[][] values = new double[size][size];
        try {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    //if(j < size)
                        values[i][j] = Double.parseDouble(nodes[i][j].getText());
                    //else if(j-size == i)values[i][j] = 1;
                }
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Неверный формат значений матрицы").show();
            return;
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Ошибка: " + e.getMessage()).show();
            return;
        }

//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size*2; j++) {
//                if(i != j)values[i][j] /= values[i][i];
//            }
//            values[i][i] = 1;
//        }

        try {
            RealMatrix A = new Array2DRowRealMatrix(values);
            DecompositionSolver solver = new LUDecomposition(A).getSolver();
            double[][] rhs = new double[size][size];
            for (int i = 0; i < size; i++) {
                rhs[i][i] = 1;
            }
            RealMatrix I = new Array2DRowRealMatrix(rhs);
            RealMatrix B = solver.solve(I);
            values = B.getData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Невозможно найти обратную матрицу");
            return;
        }

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
                TextField tf = new TextField(String.format(values[i][j] % 1.0 == 0 ? "%.0f" : "%.2f", values[i][j]));
                tf.setMaxSize(50,50);
                tf.setEditable(false);
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
        size.setText("5");
        generate(5, fractionNumCB.isSelected());
    }
}
