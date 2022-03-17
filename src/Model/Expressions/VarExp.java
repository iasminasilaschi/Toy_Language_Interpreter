package Model.Expressions;

import Exceptions.MyException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Types.IType;
import Model.Values.IValue;

public class VarExp implements IExp {
    String id;

    public VarExp(String id_) {
        id = id_;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap heap) throws MyException {
        return symTable.lookUp(id);
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv.lookUp(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public VarExp deepCopy() {
        return new VarExp(id);
    }
}
