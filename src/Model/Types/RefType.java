package Model.Types;

import Model.Values.IValue;
import Model.Values.RefValue;

public class RefType implements IType {

    IType inner;

    public RefType(IType inner_) {
        inner = inner_;
    }

    public IType getInner() {
        return inner;
    }

    public boolean equals(Object another) {
        if (another instanceof  RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref " + inner.toString();
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public IType deepCopy() {
        return new RefType(inner.deepCopy());
    }
}
