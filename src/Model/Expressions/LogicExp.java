package Model.Expressions;

import Exceptions.MyException;
import Exceptions.OperandException;
import Exceptions.TypeException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IValue;

import java.util.Objects;

public class LogicExp implements IExp {
    IExp exp1;
    IExp exp2;
    String opStr;
    int op; // 1: && (and)
            // 2: || (or)

    LogicExp(String op_, IExp exp1_, IExp exp2_) {
        exp1 = exp1_;
        exp2 = exp2_;
        opStr = op_;
        if (Objects.equals(op_, "&&")) {
            op = 1;
        }
        else if (Objects.equals(op_, "||")) {
            op = 2;
        }
        else {
            op = 0;
        }
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap heap) throws MyException {
        IValue val1;
        IValue val2;
        val1 = exp1.eval(symTable, heap);
        val2 = exp2.eval(symTable, heap);
        BoolValue b1 = (BoolValue) val1;
        BoolValue b2 = (BoolValue) val2;
        boolean bool1;
        boolean bool2;
        bool1 = b1.getVal();
        bool2 = b2.getVal();
        return switch (op) {
            case 1 -> new BoolValue(bool1 && bool2);
            case 2 -> new BoolValue(bool1 || bool2);
            default -> throw new OperandException("ERROR: The operand is not one of { &&, || }!\n");
        };
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if(type2.equals(new BoolType())) {
                return new BoolType();
            }
            else {
                throw new TypeException("ERROR: The second operand is not a boolean!\n");
            }
        }
        else {
            throw new TypeException("ERROR: The first operand is not a boolean!\n");
        }
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + opStr + " " + exp2.toString();
    }

    @Override
    public IExp deepCopy() {
        return new LogicExp(opStr, exp1.deepCopy(), exp2.deepCopy());
    }
}
