package Model.Statements;

import Exceptions.MyException;
import Exceptions.TypeException;
import Exceptions.TypeMismatchException;
import Model.ADTs.Dict;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.Expressions.IExp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class IfStmt implements IStmt {
    IExp exp;
    IStmt thenStmt;
    IStmt elseStmt;

    public IfStmt(IExp exp_, IStmt thenStmt_, IStmt elseStmt_) {
        exp = exp_;
        thenStmt = thenStmt_;
        elseStmt = elseStmt_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeapTable();
        IStack<IStmt> stack = state.getStack();
        IValue condition = exp.eval(symTable, heap);
        BoolValue boolCondition = (BoolValue) condition;
        if(boolCondition.getVal()) {
            stack.push(thenStmt);
        }
        else {
            stack.push(elseStmt);
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typeCheck(typeEnv);
        if(typeExp.equals(new BoolType())) {
            thenStmt.typeCheck(typeEnv.deepCopy());
            elseStmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else {
            throw new TypeException("ERROR: The provided condition is not a boolean!\n");
        }
    }

    @Override
    public String toString() {
        return "(if(" + exp.toString() + ") then(" + thenStmt.toString() +") else(" + elseStmt.toString() + "))";
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenStmt.deepCopy(), elseStmt.deepCopy());
    }
}
