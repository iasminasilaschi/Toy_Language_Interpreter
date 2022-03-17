package Model.Values;

import Model.Types.IType;
import Model.Types.StringType;

public class StringValue implements IValue {
    String val;

    public StringValue() {
        val = "";
    }

    public StringValue(String val_) {
        val = val_;
    }

    public String getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(val);
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object another) {
        if(another instanceof StringValue) {
            if(val.equals(((StringValue) another).getVal())) {
                return true;
            }
        }
        return false;
    }
}