package Model.Statements;

import Exceptions.MyException;
import Model.ADTs.IDict;
import Model.PrgState;
import Model.Types.IType;

public class NopStmt implements IStmt {

    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }
}
