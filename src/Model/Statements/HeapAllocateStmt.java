package Model.Statements;

import Exceptions.DeclarationException;
import Exceptions.MyException;
import Exceptions.TypeException;
import Exceptions.TypeMismatchException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Expressions.IExp;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Values.IValue;
import Model.Values.RefValue;

import java.io.IOException;

public class HeapAllocateStmt implements IStmt {
    String var_name;
    IExp expression;

    public HeapAllocateStmt(String var_name_, IExp expression_) {
        var_name = var_name_;
        expression = expression_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeapTable();
        if(!symTable.isDefined(var_name)) {
            throw new DeclarationException("ERROR: The variable is not declared!\n");
        }
        IValue value = symTable.lookUp(var_name);
        RefValue ref_value = (RefValue) value;
        IType type = ref_value.getType();
        RefType ref_type = (RefType) type;
        IValue exp_val = expression.eval(symTable, heap);
        int allocated_address = heap.getFree();
        heap.add(allocated_address, exp_val.deepCopy());
        ref_value.setAddress(allocated_address);
        symTable.update(var_name, ref_value);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookUp(var_name);
        IType typeExp = expression.typeCheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        }
        else {
            throw new TypeMismatchException("ERROR: The variable and expression evaluation have different types!\n");
        }
    }

    @Override
    public String toString() {
        return "new(" + var_name + ", " + expression.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new HeapAllocateStmt(var_name, expression.deepCopy());
    }
}
