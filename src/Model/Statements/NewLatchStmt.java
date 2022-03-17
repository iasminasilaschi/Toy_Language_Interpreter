package Model.Statements;

import Exceptions.DeclarationException;
import Exceptions.MyException;
import Exceptions.TypeException;
import Exceptions.TypeMismatchException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.ADTs.LatchTable;
import Model.Expressions.IExp;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IValue;
import Model.Values.IntValue;

import java.io.IOException;

public class NewLatchStmt implements IStmt {
    String var;
    IExp exp;

    public NewLatchStmt(String var_, IExp exp_) {
        var = var_;
        exp = exp_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeapTable();
        IStack<IStmt> stack = state.getStack();
        LatchTable latchTable = state.getLatchTable();

        IValue num = exp.eval(symTable, heap);
        if(!num.getType().equals(new IntType())){
            throw new TypeException("ERROR: The expression type of " + exp + " is different than int!\n New Latch unsuccessful!");
        }
        IntValue num1 = (IntValue) num;

        latchTable.add_new(num1.getVal());

        if(symTable.isDefined(var)) {
            IValue var_val = symTable.lookUp(var);
            if(var_val.getType().equals(new IntType())) {
                symTable.add(var, new IntValue(latchTable.get_new_free_location()));
            }
            else {
                throw new TypeMismatchException("ERROR: The variable " + var + "is not of type int!\n New Latch unsuccessful!");
            }
        }
        else {
            throw new DeclarationException("ERROR: The variable " + var + "is not defined in the symbol table!\n New Latch unsuccessful!");
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.lookUp(var);
        IType expType = exp.typeCheck(typeEnv);
        if (!varType.equals(new IntType()))
            throw new TypeException("ERROR: " + var + " is not of type int!");
        else if (!expType.equals(new IntType()))
            throw new TypeException("ERROR: " + exp + " is not of type int!");
        else
            return typeEnv;
    }

    @Override
    public String toString() {
        return "newLatch{" + "var='" + var + "', exp=" + exp + "}";
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStmt(var, exp.deepCopy());
    }
}
