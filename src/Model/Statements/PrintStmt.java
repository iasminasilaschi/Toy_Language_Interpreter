package Model.Statements;

import Exceptions.MyException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.ADTs.IList;
import Model.Expressions.IExp;
import Model.PrgState;
import Model.Types.IType;
import Model.Values.IValue;

public class PrintStmt implements IStmt {
    IExp exp;

    public PrintStmt(IExp exp_) {
        exp = exp_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IList<IValue> out = state.getOut();
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeapTable();
        out.add(exp.eval(symTable, heap));
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print (" + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }
}
