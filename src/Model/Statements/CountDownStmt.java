package Model.Statements;

import Exceptions.DeclarationException;
import Exceptions.MyException;
import Exceptions.TypeException;
import Model.ADTs.*;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IValue;
import Model.Values.IntValue;

import java.io.IOException;

public class CountDownStmt implements IStmt {
    String var;

    public CountDownStmt(String var_) {
        var = var_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeapTable();
        IStack<IStmt> stack = state.getStack();
        LatchTable latchTable = state.getLatchTable();
        IList<IValue> out = state.getOut();

        IValue found_index;
        found_index = symTable.lookUp(var);

        if(!found_index.getType().equals(new IntType())) {
            throw new TypeException("ERROR: " + var + " not of tyoe int!\n Count Down Unsuccessful!");
        }
        int found_index_value = ((IntValue) found_index).getVal();
        int found_index_latch_value = latchTable.lookUp(found_index_value);
        if(!latchTable.isDefined(found_index_value)) {
            throw new DeclarationException("ERROR: " + found_index_value + " is not an index in the Latch table!\n Count Down Unsuccessful!");
        }
        else if(found_index_latch_value > 0) {
            latchTable.add(found_index_value, found_index_latch_value - 1);
            out.add(new IntValue(state.get_id()));
        }
        else {
            out.add(new IntValue(state.get_id()));
        }

        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.lookUp(var);
        if (! varType.equals(new IntType()))
            throw new TypeException("ERROR: " + var + " is not of type int!");
        else
            return typeEnv;
    }

    @Override
    public String toString() {
        return "countDown(" + var + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new CountDownStmt(var);
    }
}
