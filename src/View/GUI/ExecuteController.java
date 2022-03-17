package View.GUI;

import Controller.Controller;
import Exceptions.FileException;
import Exceptions.MyException;
import Model.ADTs.FileTable;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.ADTs.LatchTable;
import Model.PrgState;
import Model.Statements.IStmt;

import Model.Values.IValue;
import Model.Values.StringValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class ExecuteController {
    private final Controller controller;

    public ExecuteController(Controller controller_) {
        controller = controller_;
    }

    @FXML
    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    private ListView<Integer> idsListView;

    @FXML
    private final ObservableList<Integer> observableList = FXCollections.observableArrayList();

    @FXML
    private TextArea exeStackTextArea;

    @FXML
    private TableView<HashMap.Entry<String, String>> symbol_table;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> symbol_table_names;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> symbol_table_values;

    @FXML
    private TableView<HashMap.Entry<String, String>> file_table;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> file_table_names;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> file_table_values;

    @FXML
    private TableView<HashMap.Entry<Integer, String>> heap_table;

    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, Integer> heap_table_names;

    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, String> heap_table_values;

    @FXML
    private TableView<HashMap.Entry<Integer, Integer>> latch_table;

    @FXML
    private TableColumn<HashMap.Entry<Integer, Integer>, Integer> latch_table_names;

    @FXML
    private TableColumn<HashMap.Entry<Integer, Integer>, Integer> latch_table_values;

    @FXML
    private Button oneStepButton;

    @FXML
    private TextArea outTextArea;

    @FXML
    private Button changeIDButton;

    @FXML
    public void initialize() {
        try {
            observableList.setAll(controller.getIDs().values());
            idsListView.setItems(observableList);
            controller.startOneStep();
            exeStackTextArea.setText(controller.getExeStack(0));
        }
        catch (MyException me) {
            controller.endOneStep();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: Execution Stack Empty!");
            alert.show();
        }
    }

    @FXML
    void onChangeIDButtonAction(ActionEvent event) {
        int selectedID = idsListView.getSelectionModel().getSelectedIndex();
        if(selectedID == -1) {
            selectedID = 0;
        }
        reset(selectedID);
    }

    @FXML
    void onOneStepButtonAction(ActionEvent event) {
        try {
            controller.oneStep();
            if (!idsListView.getItems().isEmpty()) {
                idsListView.getSelectionModel().select(0);
            }
            reset(idsListView.getSelectionModel().getSelectedIndex());
        } catch (MyException | IOException | InterruptedException e) {
            controller.endOneStep();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: Execution Stack Empty!");
            alert.show();
        }
    }

    public void reset(int id) {
        try {
            observableList.setAll(controller.getIDs().values());
            idsListView.setItems(observableList);
            outTextArea.setText(controller.getOut(id));
            exeStackTextArea.setText(controller.getExeStack(id));

            IDict<String, IValue> symbolTable= controller.getSymTable(id);
            List<Map.Entry<String, String>> symbolTableList=new ArrayList<>();
            for(Map.Entry<String, IValue> element:symbolTable.getContent().entrySet()){
                Map.Entry<String, String> el=new AbstractMap.SimpleEntry<String, String>(element.getKey(),element.getValue().toString());
                symbolTableList.add(el);
            }
            symbol_table.setItems(FXCollections.observableList(symbolTableList));
            symbol_table.refresh();

            symbol_table_names.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()));
            symbol_table_values.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));


            Heap heapTable = controller.getHeap(id);
            List<Map.Entry<Integer, String>> heapTableList=new ArrayList<>();
            for(Map.Entry<Integer, IValue> element:heapTable.getContent().entrySet()){
                Map.Entry<Integer, String> el=new AbstractMap.SimpleEntry<Integer, String>(element.getKey(),element.getValue().toString());
                heapTableList.add(el);
            }
            heap_table.setItems(FXCollections.observableList(heapTableList));
            heap_table.refresh();

            heap_table_names.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getKey()).asObject());
            heap_table_values.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));


            FileTable fileTable= controller.getFileTable(id);
            List<Map.Entry<String, String>> fileList = new ArrayList<>();
            for(Map.Entry<StringValue, BufferedReader> element:fileTable.getContent().entrySet()){
                Map.Entry<String, String> el=new AbstractMap.SimpleEntry<String, String>(element.getKey().toString(),element.getValue().toString());
                fileList.add(el);
            }
            file_table.setItems(FXCollections.observableList(fileList));
            file_table.refresh();

            file_table_names.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()));
            file_table_values.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));


            LatchTable latchTable = controller.getLatch(id);
            List<Map.Entry<Integer, Integer>> latchList = new ArrayList<>(latchTable.getContent().entrySet());
            latch_table.setItems(FXCollections.observableList(latchList));
            latch_table.refresh();
            latch_table_names.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
            latch_table_values.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());

            if(controller.getExeStack(id).isEmpty()) {
                controller.endOneStep();
            }
        }
        catch (MyException me) {
            controller.endOneStep();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: Execution Stack Empty!");
            alert.show();
        }

    }

}
