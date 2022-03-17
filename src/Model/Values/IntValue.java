package Model.Values;

import Model.Types.IType;
import Model.Types.IntType;

public class IntValue implements IValue {
    int val;

    public IntValue() {
        val = 0;
    }

    public IntValue(int val_) {
        val = val_;
    }

    public int getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(val);
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object another) {
        if(another instanceof IntValue) {
            if(val == ((IntValue) another).getVal()) {
                return true;
            }
        }
        return false;
    }
}