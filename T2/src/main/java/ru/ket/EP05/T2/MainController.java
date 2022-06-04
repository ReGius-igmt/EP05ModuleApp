package ru.ket.EP05.T2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.controller.Controller;

public class MainController implements Controller {

    @FXML
    private GridPane grid;

    private Node[][] nodes;

    @FXML
    void onResult(ActionEvent event) {
        for (Node[] ns : nodes){
            for(Node n : ns){
                if(n instanceof Text) System.out.print(((Text)n).getText() + " ");
                if(n instanceof TextField) System.out.print(((TextField)n).getText() + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void init(SimpleViewHandler vh) {
        for (int i = 0; i < 6; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints());
        }
        for (int i = 0; i < 6; i++) {
            grid.getRowConstraints().add(new RowConstraints());
        }
        String[] titles = new String[]{"i", "xi", "yi", "Δyi", "Δ^2yi", "Δ^3yi"};
        nodes = new Node[6][6];
        for (int i = 0; i < 6; i++) {
            grid.add(new Text(titles[i]), i, 0);
            if(i < 5)grid.add(new Text(i+""), 0, i+1);
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                nodes[i][j] = new TextField("0");
            }
        }

        for (int i = 0; i < 5 ; i++) {
            for (int j = 0; j < 5; j++) {
                grid.add(nodes[i][j], j+1, i+1);
            }
        }
    }
}
