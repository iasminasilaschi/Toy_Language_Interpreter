package Model.Statements;

import Exceptions.DeclarationException;
import Exceptions.MyException;
import Exceptions.TypeException;
import Exceptions.TypeMismatchException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.ADTs.LatchTable;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IValue;
import Model.Values.IntValue;

import java.io.IOException;

public class AwaitStmt implements IStmt {
    String var;

    public AwaitStmt(String var_) {
        var = var_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeapTable();
        IStack<IStmt> stack = state.getStack();
        LatchTable latchTable = state.getLatchTable();

        IValue found_index;
        if(!symTable.isDefined(var)) {
            throw new DeclarationException("ERROR: The variable is not in the symbol table!\n Await Latch unsuccessful!");
        }
        found_index = symTable.lookUp(var);
        if (!found_index.getType().equals(new IntType())) {
            throw new TypeException("ERROR: " + var + " not of type int!\nAwait Latch unsuccessful!");
        }

        int found_index_value = ((IntValue) found_index).getVal();
        if(!latchTable.isDefined(found_index_value)) {
            throw new DeclarationException("ERROR: " + found_index_value + " is not an index in the latch table!\n Await Latch unsuccessful!");
        }
        else if (latchTable.lookUp(found_index_value) == 0) {
            // do nothing
        }
        else {
            stack.push(this);
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.lookUp(var);
        if (!varType.equals(new IntType()))
            throw new TypeException("ERROR: " + var + " is not of type int!");
        else
            return typeEnv;
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }
}
