package Model.Statements;

import Exceptions.MyException;
import Exceptions.TypeException;
import Model.ADTs.IDict;
import Model.ADTs.Stack;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;

import java.io.IOException;

public class ForkStmt implements IStmt {
    IStmt stmt;

    public ForkStmt(IStmt stmt_) {
        stmt = stmt_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        return new PrgState(new Stack<>(), state.getSymTableDeepCopy(), state.getOut(), stmt,
                state.getFileTable(), state.getHeapTable(), state.getLatchTable());
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return stmt.typeCheck(typeEnv.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + stmt + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }
}
