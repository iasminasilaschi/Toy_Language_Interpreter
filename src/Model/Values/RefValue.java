package Model.Values;

import Model.Types.IType;
import Model.Types.RefType;

public class RefValue implements IValue {
    int address;
    IType locationType;

    public RefValue(int address_, IType locationType_) {
        address = address_;
        locationType = locationType_;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address_) {
        address = address_;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType.deepCopy());
    }

        @Override
    public IValue defaultValue() {
        return null;
    }
}
