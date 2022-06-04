package ru.ket.EP05.T0;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.controller.Controller;

import java.net.URL;
import java.util.*;
import java.util.List;

public class MainController implements Controller {

    @FXML
    private VBox distances;

    @FXML
    private VBox pointsBox;
    private List<Point> points = new ArrayList<>();

    @Override
    public void init(SimpleViewHandler vh) {

        ChangeListener<String> onPointUpdate = (observableValue, s, t1) -> {
            distances.getChildren().clear();
            Map<String, Double> distancess = new HashMap<>();
            for (int i = 0; i < points.size(); i++) {
                for (int j = i+1; j < points.size(); j++) {
                    try{
                        Point p1 = points.get(i);
                        Point p2 = points.get(j);
                        double p1x = getValue(p1.getXField());
                        double p1y = getValue(p1.getYField());
                        double p1z = getValue(p1.getZField());
                        double p2x = getValue(p2.getXField());
                        double p2y = getValue(p2.getYField());
                        double p2z = getValue(p2.getZField());
                        double res = Math.sqrt(Math.pow(p1x-p2x, 2) + Math.pow(p1y-p2y, 2) + Math.pow(p1z-p2z, 2));
                        distancess.put(p1.getName()+p2.getName(), res);
                    }catch (Exception ignored){}
                }
            }
            distancess.entrySet().stream().sorted(Comparator.comparingDouble(Map.Entry::getValue)).forEach(e->{
                Text t = new Text(e.getKey() + " " + String.format("%.2f", e.getValue()));
                t.setFont(Font.font(14));
                if(distances.getChildren().size() < 2){
                    t.setFill(Paint.valueOf("red"));
                    t.setFont(Font.font("System", FontWeight.BOLD, 14));
                }
                distances.getChildren().add(t);
            });
        };

        String[] names = new String[]{"A", "B", "C", "D", "E"};
        for (int i = 0; i < 5; i++) {
            Point point = new Point(names[i]);
            point.addListener(onPointUpdate);
            points.add(point);
            pointsBox.getChildren().add(point);
        }
        onPointUpdate.changed(null, null, null);
    }

    private Double getValue(TextField f) throws NumberFormatException{
        try {
            f.getStyleClass().remove("error");
            return Double.parseDouble(f.getText());
        } catch (NumberFormatException e) {
            f.getStyleClass().add("error");
            throw e;
        }
    }
}
