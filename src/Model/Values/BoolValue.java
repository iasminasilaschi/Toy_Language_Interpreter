package Model.Values;

import Model.Types.BoolType;
import Model.Types.IType;

public class BoolValue implements IValue {
    boolean val;

    public BoolValue() {
        val = false;
    }

    public BoolValue(boolean val_) {
        val = val_;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        if(val) {
            return "true";
        }
        else {
            return "false";
        }
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(val);
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object another) {
        if(another instanceof BoolValue) {
            if(val == ((BoolValue) another).getVal()) {
                return true;
            }
        }
        return false;
    }
}