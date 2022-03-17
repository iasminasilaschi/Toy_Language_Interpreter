package Model.Types;

import Model.Values.IValue;
import Model.Values.StringValue;

public class StringType implements IType {

    @Override
    public boolean equals(Object another) {
        if(another instanceof StringType) {
            return true;
        }
        else return false;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public IValue defaultValue() {
        return new StringValue().defaultValue();
    }
}