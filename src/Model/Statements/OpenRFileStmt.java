package Model.Statements;

import Exceptions.*;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Expressions.IExp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.StringType;
import Model.Values.IValue;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt {

    private IExp exp;

    public OpenRFileStmt(IExp exp_) {
        exp = exp_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeapTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        IValue value = exp.eval(symTable, heap);
        StringValue strValue = (StringValue) value;
        if(fileTable.isDefined(strValue)) {
            throw new FileDeclaredException("ERROR: The file is already declared!\n");
        }
        try {
            BufferedReader file = new BufferedReader(new FileReader(strValue.getVal()));
            fileTable.add(strValue, file);
            return null;
        }
        catch (IOException ioe) {
            throw new InvalidFileException("ERROR: The file is invalid!\n");
        }
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typeCheck(typeEnv);
        if(typeExp.equals(new StringType())) {
            return typeEnv;
        }
        else {
            throw new TypeMismatchException("ERROR: The file name is not of type string!\n");
        }
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }
}