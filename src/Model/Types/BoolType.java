package Model.Types;

import Model.Values.BoolValue;
import Model.Values.IValue;

public class BoolType implements IType {
    @Override
    public boolean equals(Object another) {
        if(another instanceof BoolType) {
            return true;
        }
        else return false;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue().defaultValue();
    }
}
