package Model.Statements;

import Exceptions.MyException;
import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.PrgState;
import Model.Types.IType;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt second;

    public CompStmt(IStmt first_, IStmt second_) {
        first = first_;
        second = second_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public String toString() {
        return first.toString() + "; " + second.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}
