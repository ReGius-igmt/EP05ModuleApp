package ru.ket.EP05.T5;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import ru.ket.EP05.T5.model.Call;
import ru.ket.EP05.T5.model.Country;
import ru.regiuss.EP05.core.SimpleViewHandler;
import ru.regiuss.EP05.core.controller.Controller;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainController implements Controller {

    private SimpleViewHandler vh;
    private HashMap<String, List<Call>> callsMap;
    private HashMap<Integer, Country> countries;

    @FXML
    private ChoiceBox<String> abonentsBox;

    @FXML
    private ChoiceBox<Country> countryBox;

    @FXML
    private TableView<Call> calls;

    @FXML
    private TableView<Country> countriesTable;

    @FXML
    private TextField conversationsPathField;

    @FXML
    private TextField countriesPathField;

    @FXML
    private TextField minutesField;

    @FXML
    private TextField resField;

    @FXML
    void onSelectConversationsPath(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Выбор файла");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        chooser.setInitialDirectory(vh.getModule().getWorkDirectory());
        File f = chooser.showOpenDialog(((Node)event.getTarget()).getScene().getWindow());
        if(f != null)conversationsPathField.setText(f.getAbsolutePath());
    }

    @FXML
    void onSelectCountriesPath(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Выбор файла");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        chooser.setInitialDirectory(vh.getModule().getWorkDirectory());
        File f = chooser.showOpenDialog(((Node)event.getTarget()).getScene().getWindow());
        if(f != null)countriesPathField.setText(f.getAbsolutePath());
    }

    @FXML
    void onPathsUpdate(ActionEvent event) {
        File conversationsF = new File(conversationsPathField.getText());
        File countyF = new File(countriesPathField.getText());
        if(!(conversationsF.exists() || countyF.exists()))return;
        try(
                FileReader stream = new FileReader(countyF);
                BufferedReader buffer = new BufferedReader(stream);
        ){
            String line;
            countries = new HashMap<>();
            while ((line = buffer.readLine()) != null){
                try {
                    String[] data = line.split(";");
                    if(data.length < 2)continue;
                    countries.put(Integer.parseInt(data[0]), new Country(Integer.parseInt(data[0]), Integer.parseInt(data[2]), data[1]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            countriesTable.setItems(FXCollections.observableList(countries.values().stream().toList()));
            countries.put(0, new Country(0, 0, "Все"));
        }catch (Exception e){
            e.printStackTrace();
        }

        try(
                FileReader stream = new FileReader(conversationsF);
                BufferedReader buffer = new BufferedReader(stream);
        ){
            String line;
            callsMap = new HashMap<>();
            while ((line = buffer.readLine()) != null){
                try {
                    String[] data = line.split(";");
                    if(data.length < 3 || !countries.containsKey(Integer.parseInt(data[1])))continue;
                    List<Call> callList = callsMap.computeIfAbsent(data[0], k -> new ArrayList<>());
                    Call call = new Call(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                    callList.add(call);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        abonentsBox.setItems(FXCollections.observableList(callsMap.keySet().stream().toList()));
        abonentsBox.getSelectionModel().select(0);
    }

    @Override
    public void init(SimpleViewHandler vh) {
        this.vh = vh;
        vh.getModule().getWorkDirectory().mkdirs();
        Path wd = vh.getModule().getWorkDirectory().toPath();
        countriesPathField.setText(wd.resolve("countries.txt").toAbsolutePath().toString());
        conversationsPathField.setText(wd.resolve("conversations.txt").toAbsolutePath().toString());
        if(!wd.resolve("conversations.txt").toFile().exists()){
            for(String s : new String[]{"conversations.txt", "countries.txt"}){
                try(
                        FileOutputStream os = new FileOutputStream(wd.resolve(s).toFile());
                        InputStream is = getClass().getResourceAsStream("/files/" + s)
                        ){
                    os.write(is.readAllBytes());
                }catch (Exception ignored){
                    ignored.printStackTrace();
                }
            }
        }

        TableColumn<Call, String> column = new TableColumn<>("Телефон");
        column.setCellValueFactory(call -> new SimpleStringProperty(call.getValue().getPhone()));
        calls.getColumns().add(column);
        column = new TableColumn<>("Страна");
        column.setCellValueFactory(call -> new SimpleStringProperty(countries.get(call.getValue().getCountryCode()).getName()));
        calls.getColumns().add(column);
        column = new TableColumn<>("Минуты");
        column.setCellValueFactory(call -> new SimpleStringProperty(call.getValue().getMinutes() + ""));
        calls.getColumns().add(column);
        column = new TableColumn<>("Тариф (руб./мин)");
        column.setCellValueFactory(call -> new SimpleStringProperty(countries.get(call.getValue().getCountryCode()).getPrice() + ""));
        calls.getColumns().add(column);
        column = new TableColumn<>("Итог (руб.)");
        column.setCellValueFactory(call -> new SimpleStringProperty((countries.get(call.getValue().getCountryCode()).getPrice() * call.getValue().getMinutes()) + ""));
        calls.getColumns().add(column);

        TableColumn<Country, String> countryColumn = new TableColumn<>("id");
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        countriesTable.getColumns().add(countryColumn);
        countryColumn = new TableColumn<>("Название");
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countriesTable.getColumns().add(countryColumn);
        countryColumn = new TableColumn<>("Тариф");
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        countriesTable.getColumns().add(countryColumn);


        abonentsBox.setOnAction(event -> {
            if(abonentsBox.getSelectionModel().getSelectedItem() == null)return;
            List<Call> calls1 = callsMap.get(abonentsBox.getSelectionModel().getSelectedItem());
            Set<Integer> countiesIds = calls1.stream().map(Call::getCountryCode).collect(Collectors.toSet());
            countryBox.setItems(FXCollections.observableList(
                    countries.values().stream()
                            .filter(country -> country.getId() == 0 || countiesIds.contains(country.getId()))
                            .toList()
            ));
            countryBox.getSelectionModel().select(0);
        });

        countryBox.setOnAction(event -> {
            if(countryBox.getSelectionModel().getSelectedItem() == null)return;
            int id = countryBox.getSelectionModel().getSelectedItem().getId();
            int minutes = 0;
            int price = 0;
            List<Call> calls1 = callsMap.get(abonentsBox.getSelectionModel().getSelectedItem());
            calls.setItems(FXCollections.observableArrayList());
            for(Call c : calls1){
                if(id == 0 || c.getCountryCode() == id){
                    calls.getItems().add(c);
                    minutes += c.getMinutes();
                    price += c.getMinutes() * countries.get(c.getCountryCode()).getPrice();
                }
            }
            resField.setText(price + "");
            minutesField.setText(minutes + "");
        });

        onPathsUpdate(null);
    }
}
