package Model.Types;

import Model.Values.IValue;
import Model.Values.IntValue;

public class IntType implements IType {

    @Override
    public boolean equals(Object another) {
        if(another instanceof IntType) {
            return true;
        }
        else return false;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public IValue defaultValue() {
        return new IntValue().defaultValue();
    }
}
