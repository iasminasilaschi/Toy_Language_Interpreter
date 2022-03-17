package Model.Statements;

import Exceptions.DeclarationException;
import Exceptions.MyException;
import Exceptions.TypeMismatchException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Expressions.IExp;
import Model.PrgState;
import Model.Types.IType;
import Model.Values.IValue;

public class AssignStmt implements IStmt {
    String id;
    IExp exp;

    public AssignStmt(String id_, IExp exp_) {
        id = id_;
        exp = exp_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        Heap heap = state.getHeapTable();
        if (symTable.isDefined(id)) {
            IValue val = exp.eval(symTable, heap);
            symTable.update(id, val);
        }
        else throw new DeclarationException("ERROR: The used variable " + id + " was not declared before!\n");
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookUp(id);
        IType typeExp = exp.typeCheck(typeEnv);
        if(typeVar.equals(typeExp)) {
            return typeEnv;
        }
        else {
            throw new TypeMismatchException("ERROR: Declared type of variable \"" + id +
                    "\" and type of the assigned expression do not match!\n");
        }
    }

    @Override
    public String toString() {
            return id + " = " + exp.toString();
    }

    @Override
    public AssignStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }
}
