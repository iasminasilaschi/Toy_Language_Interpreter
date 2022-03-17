package Model.Statements;

import Exceptions.MyException;
import Model.ADTs.IDict;
import Model.PrgState;
import Model.Types.IType;

import java.io.IOException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException, IOException;
    IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException;
    String toString();
    IStmt deepCopy();
}
