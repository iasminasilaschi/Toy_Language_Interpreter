package Model.Statements;

import Exceptions.*;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Expressions.IExp;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.StringType;
import Model.Values.IValue;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    IExp exp;

    public CloseRFileStmt(IExp exp_) {
        exp = exp_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeapTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        IValue value = exp.eval(symTable, heap);
        StringValue strValue = (StringValue) value;
        if(!(fileTable.isDefined(strValue))) {
            throw new FileDeclaredException("ERROR: The file is not declared!\n");
        }
        BufferedReader reader = fileTable.lookUp(strValue);
        try {
            reader.close();
            fileTable.remove(strValue);
        }
        catch (IOException ioe) {
            throw new FileException("ERROR: Cannot close file!\n");
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typeCheck(typeEnv);
        if(typeExp.equals(new StringType())) {
            return typeEnv;
        }
        else {
            throw new TypeMismatchException("ERROR: Type is not string!\n");
        }
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(exp.deepCopy());
    }
}
