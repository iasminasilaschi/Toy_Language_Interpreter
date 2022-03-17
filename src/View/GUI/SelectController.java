package View.GUI;

import Controller.Controller;
import Exceptions.MyException;
import Exceptions.TypeException;
import Model.ADTs.*;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.FileRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SelectController {

    private final ArrayList<Controller> controllers = new ArrayList<>();

    @FXML
    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    private ListView<IStmt> listViewExamples;

    @FXML
    private Button executeButton;

    @FXML
    void onExecuteButtonAction(ActionEvent event) throws IOException, MyException {
        int selectedID = listViewExamples.getSelectionModel().getSelectedIndex();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("execute.fxml"));
        fxmlLoader.setController(new ExecuteController(controllers.get(selectedID)));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("Execution");
        stage.setScene(new Scene(root, 850, 750));
        stage.showAndWait();
    }

    @FXML
    private final ObservableList<IStmt> observableList = FXCollections.observableArrayList();


    public static IStmt makeIt(IStmt... stmtList){
        if(stmtList.length==0)return new NopStmt();
        return new CompStmt(stmtList[0],makeIt(Arrays.copyOfRange(stmtList,1,stmtList.length)));
    }

    @FXML
    public void initialize() throws MyException {

        IStmt ex1 = makeIt(new VarDeclStmt("a", new RefType(new IntType())),
                new VarDeclStmt("b", new RefType(new IntType())),
                new VarDeclStmt("v", new IntType()),
                new HeapAllocateStmt("a", new ValueExp(new IntValue(0))),
                new HeapAllocateStmt("b", new ValueExp(new IntValue(0))),
                new HeapWriteStmt("a", new ValueExp(new IntValue(1))),
                new HeapWriteStmt("b", new ValueExp(new IntValue(2))),
                new ConditionalAssignmentStmt("v", new RelationalExp("<", new HeapReadExp(new VarExp("a")), new HeapReadExp(new VarExp("b"))), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                new PrintStmt(new VarExp("v")),
                new ConditionalAssignmentStmt("v", new RelationalExp(">", new ArithExp('-', new HeapReadExp(new VarExp("b")), new ValueExp(new IntValue(2))), new HeapReadExp(new VarExp("a"))), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                new PrintStmt(new VarExp("v")));

        IStmt ex2 = makeIt(new VarDeclStmt("v1",new RefType(new IntType())),new VarDeclStmt("v2",new RefType(new IntType())),
                new VarDeclStmt("v3",new RefType(new IntType())),new VarDeclStmt("cnt",new IntType()),
                new HeapAllocateStmt("v1",new ValueExp(new IntValue(2))),new HeapAllocateStmt("v2",new ValueExp(new IntValue(3))),
                new HeapAllocateStmt("v3",new ValueExp(new IntValue(4))),new NewLatchStmt("cnt",new HeapReadExp(new VarExp("v2"))),
                new ForkStmt(makeIt(new HeapWriteStmt("v1",new ArithExp('*',new HeapReadExp(new VarExp("v1")),new ValueExp(new IntValue(10)))),
                        new PrintStmt(new HeapReadExp(new VarExp("v1"))),new CountDownStmt("cnt"),
                        new ForkStmt(makeIt(new HeapWriteStmt("v2",new ArithExp('*',new HeapReadExp(new VarExp("v2")),new ValueExp(new IntValue(10)))),
                                new PrintStmt(new HeapReadExp(new VarExp("v2"))),new CountDownStmt("cnt"),
                                new ForkStmt(makeIt(new HeapWriteStmt("v3",new ArithExp('*',new HeapReadExp(new VarExp("v3")),new ValueExp(new IntValue(10)))),
                                        new PrintStmt(new HeapReadExp(new VarExp("v3"))),new CountDownStmt("cnt"))))))),
                new AwaitStmt("cnt"),new PrintStmt(new ValueExp(new IntValue(100))),new CountDownStmt("cnt"),new PrintStmt(new ValueExp(new IntValue(100))));

        observableList.add(ex1);
        observableList.add(ex2);

        try {
            ex1.typeCheck(new Dict<>());
            PrgState program1 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex1, new FileTable(), new Heap(), new LatchTable());
            FileRepository repo1 = new FileRepository(program1, "log1.txt");
            Controller controller1 = new Controller(repo1);

            ex2.typeCheck(new Dict<>());
            PrgState program2 = new PrgState(new Stack<>(), new Dict<>(), new List<>(), ex2, new FileTable(), new Heap(), new LatchTable());
            FileRepository repo2 = new FileRepository(program2, "log2.txt");
            Controller controller2 = new Controller(repo2);

            controllers.add(controller1);
            controllers.add(controller2);
            listViewExamples.setItems(observableList);
            if (!listViewExamples.getItems().isEmpty()) {
                listViewExamples.getSelectionModel().select(0);
            }

        } catch (MyException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: Type check failed!");
            alert.show();
            throw new TypeException("ERROR: Type check failed!");
        }
    }
}
