package Model.Values;

import Model.Types.IType;

public interface IValue {
    IType getType();
    String toString();
    IValue deepCopy();
    IValue defaultValue();
    boolean equals(Object another);
}
