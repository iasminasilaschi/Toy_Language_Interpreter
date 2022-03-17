package Model.Expressions;

import Exceptions.MyException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Types.IType;
import Model.Values.IValue;

public interface IExp {
    IValue eval(IDict<String, IValue> symTable, Heap heap) throws MyException;
    IType typeCheck(IDict<String, IType> typeEnv) throws MyException;
    String toString();
    IExp deepCopy();
}
