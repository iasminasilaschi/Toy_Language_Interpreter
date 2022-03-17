package Model.Expressions;

import Exceptions.MyException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Types.IType;
import Model.Values.IValue;

public class ValueExp implements IExp {
    IValue val;

    public ValueExp(IValue val_) {
        val = val_;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap heap) throws MyException {
        return val;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return val.getType();
    }

    @Override
    public String toString() {
        return val.toString();
    }

    @Override
    public IExp deepCopy() {
        return new ValueExp(val.deepCopy());
    }
}
