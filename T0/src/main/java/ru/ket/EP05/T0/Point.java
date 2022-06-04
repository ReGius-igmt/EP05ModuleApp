package ru.ket.EP05.T0;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Point extends HBox {
    private final Text name;
    private final TextField x;
    private final TextField y;
    private final TextField z;

    public Point(String name){
        setSpacing(5);
        setAlignment(Pos.CENTER);
        this.name = new Text(name);
        x = new TextField("0");
        x.setPromptText("X");
        x.setPrefWidth(50);
        y = new TextField("0");
        y.setPromptText("Y");
        y.setPrefWidth(50);
        z = new TextField("0");
        z.setPromptText("Z");
        z.setPrefWidth(50);
        getChildren().addAll(this.name, x, y, z);
    }

    public String getName() {
        return name.getText();
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public String getX() {
        return x.getText();
    }

    public void setX(String x) {
        this.x.setText(x);
    }

    public String  getY() {
        return y.getText();
    }

    public void setY(String y) {
        this.y.setText(y);
    }

    public String getZ() {
        return z.getText();
    }

    public TextField getZField() {
        return z;
    }

    public TextField getXField() {
        return x;
    }

    public TextField getYField() {
        return y;
    }

    public void setZ(String z) {
        this.z.setText(z);
    }

    public void addListener(ChangeListener<String> listener){
        x.textProperty().addListener(listener);
        y.textProperty().addListener(listener);
        z.textProperty().addListener(listener);
    }
}
