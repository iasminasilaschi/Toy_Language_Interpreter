package Model.Statements;

import Exceptions.MyException;
import Exceptions.TypeException;
import Exceptions.TypeMismatchException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.Expressions.IExp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Values.BoolValue;
import Model.Values.IValue;

import java.io.IOException;

public class WhileStmt implements IStmt {
    IExp expression;
    IStmt thenStmt;


    public WhileStmt(IExp expression_, IStmt thenStmt_) {
        expression = expression_;
        thenStmt = thenStmt_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeapTable();
        IStack<IStmt> stack = state.getStack();
        IValue condition = expression.eval(symTable, heap);
        BoolValue boolCondition = (BoolValue) condition;
        if(boolCondition.getVal()) {
            stack.push(new WhileStmt(expression, thenStmt));
            stack.push(thenStmt);
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeExp = expression.typeCheck(typeEnv);

        if(typeExp.equals(new BoolType())) {
            thenStmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else {
            throw new TypeException("ERROR: The provided condition is not a boolean!\n");
        }
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ") {" + thenStmt.toString() +";}";
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(), thenStmt.deepCopy());
    }
}
