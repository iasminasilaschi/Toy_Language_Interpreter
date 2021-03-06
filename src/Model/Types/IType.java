package Model.Types;

import Model.Values.IValue;

public interface IType {
    boolean equals(Object another);
    String toString();
    IType deepCopy();
    IValue defaultValue();
}
