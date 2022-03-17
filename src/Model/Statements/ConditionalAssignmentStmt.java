package Model.Statements;

import Exceptions.MyException;
import Exceptions.TypeException;
import Exceptions.TypeMismatchException;
import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.Expressions.IExp;
import Model.Expressions.VarExp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;

import java.io.IOException;

public class ConditionalAssignmentStmt implements IStmt {
    String v;
    IExp exp1;
    IExp exp2;
    IExp exp3;

    public ConditionalAssignmentStmt(String v_, IExp exp1_, IExp exp2_, IExp exp3_) {
        v = v_;
        exp1 = exp1_;
        exp2 = exp2_;
        exp3 = exp3_;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IStack<IStmt> stack = state.getStack();
        IStmt if_stmt = new AssignStmt(v, exp2);
        IStmt else_stmt = new AssignStmt(v, exp3);
        IStmt new_stmt = new IfStmt(exp1, if_stmt, else_stmt);
        stack.push(new_stmt);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType type_v = new VarExp(v).typeCheck(typeEnv);
        IType type_exp1 = exp1.typeCheck(typeEnv);
        IType type_exp2 = exp2.typeCheck(typeEnv);
        IType type_exp3 = exp3.typeCheck(typeEnv);

        if(!type_exp1.equals(new BoolType())) {
            throw new TypeException("ERROR: The condition does not have type bool!");
        }
        if(!(type_exp2.equals(type_v) && (type_exp3.equals(type_v)))) {
            throw new TypeMismatchException("ERROR: The statements do not have the same types!");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return v + "= (" + exp1.toString() + ") ? " + exp2.toString() + " : " + exp3.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new ConditionalAssignmentStmt(v, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy());
    }
}
