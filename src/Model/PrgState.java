package Model;

import Exceptions.EmptyStackException;
import Exceptions.MyException;
import Model.ADTs.*;
import Model.Statements.IStmt;
import Model.Values.IValue;

import java.io.IOException;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDict<String, IValue> symTable;
    private IList<IValue> out;
    private FileTable fileTable;
    private Heap heapTable;
    private LatchTable latchTable;
    private final int id;
    private static int current_id = 1;
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack_, IDict<String, IValue> symTable_, IList<IValue> out_,
                    FileTable fileTable_, Heap heapTable_, LatchTable latchTable_) {
        exeStack = exeStack_;
        symTable = symTable_;
        out = out_;
        fileTable = fileTable_;
        heapTable = heapTable_;
        latchTable = latchTable_;
        originalProgram = null;
        id = getId();
    }

    public PrgState(IStack<IStmt> exeStack_, IDict<String, IValue> symTable_, IList<IValue> out_, IStmt program,
                    FileTable fileTable_, Heap heapTable_, LatchTable latchTable_) {
        exeStack = exeStack_;
        symTable = symTable_;
        out = out_;
        fileTable = fileTable_;
        heapTable = heapTable_;
        latchTable = latchTable_;
        exeStack.push(program);
        id = getId();
        originalProgram = program.deepCopy();
    }

    public Boolean isNotCompleted() {
        return !(exeStack.isEmpty());
    }

    public PrgState oneStep() throws MyException, IOException {
        if(exeStack.isEmpty()) {
            throw new EmptyStackException("the stack is empty");
        }
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public IStack<IStmt> getStack() {
        return exeStack;
    }

    public IDict<String, IValue> getSymTable() {
        return symTable;
    }

    public IDict<String, IValue> getSymTableDeepCopy() {
        return symTable.deepCopy();
    }

    public IList<IValue> getOut() {
        return out;
    }

    public FileTable getFileTable() {
        return fileTable;
    }

    public Heap getHeapTable() {
        return heapTable;
    }

    public LatchTable getLatchTable() {
        return latchTable;
    }

    public void setExeStack(IStack<IStmt> exeStack_) {
        exeStack = exeStack_;
    }

    public void setSymTable(IDict<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setOut(IList<IValue> out_) {
        out = out_;
    }

    public void setFileTable(FileTable fileTable_) {
        fileTable = fileTable_;
    }

    public void setHeapTable(Heap heapTable_) {
        heapTable = heapTable_;
    }

    public void setLatchTable(LatchTable latchTable_) {
        latchTable = latchTable_;
    }

    private static synchronized int getId() {
        return current_id++;
    }

    public int get_id() {
        return id;
    }

    @Override
    public String toString() {
        return "Id: " +
                id + "\n" +
                "ExeStack:\n" +
                exeStack.toString() +
                "SymTable:\n" +
                symTable.toString() +
                "Out:\n" +
                out.toString() +
                "FileTable:\n" +
                fileTable.toString() +
                "Heap:\n" +
                heapTable.toString() +
                "LatchTable:\n" +
                latchTable.toString() +
                "--------------------------------------------------------------------------------------" +
                "------------------------------------------------------------------------------------\n";
    }
}
