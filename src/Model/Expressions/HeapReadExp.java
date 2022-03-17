package Model.Expressions;

import Exceptions.DeclarationException;
import Exceptions.MyException;
import Exceptions.TypeException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Values.IValue;
import Model.Values.RefValue;

public class HeapReadExp implements IExp {
    IExp expression;

    public HeapReadExp(IExp expression_) {
        expression = expression_;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap heap) throws MyException {
        IValue value = expression.eval(symTable, heap);
        RefValue ref_value = (RefValue) value;
        int address = ref_value.getAddress();
        if(!(heap.isDefined(address))) {
            throw new DeclarationException("ERROR: The address is not defined in the heap!\n");
        }
        return heap.lookUp(address);
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType type = expression.typeCheck(typeEnv);
        if(type instanceof RefType refType) {
            return refType.getInner();
        }
        else {
            throw new TypeException("ERROR: The value is not of type Ref!\n");
        }
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }

    @Override
    public IExp deepCopy() {
        return null;
    }
}
