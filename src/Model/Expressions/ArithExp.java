package Model.Expressions;

import Exceptions.DivisionByZeroException;
import Exceptions.MyException;
import Exceptions.OperandException;
import Exceptions.TypeException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IValue;
import Model.Values.IntValue;

public class ArithExp implements IExp {
    IExp exp1;
    IExp exp2;
    char opStr;
    int op; // 1: + (sum)
            // 2: - (difference)
            // 3: * (multiplication)
            // 4: / (division)

    public ArithExp(char op_, IExp exp1_, IExp exp2_) {
        exp1 = exp1_;
        exp2 = exp2_;
        opStr = op_;
        if(op_ == '+') {
            op = 1;
        }
        else if(op_ == '-') {
            op = 2;
        }
        else if(op_ == '*') {
            op = 3;
        }
        else if(op_ == '/') {
            op = 4;
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
        IntValue int1 = (IntValue) val1;
        IntValue int2 = (IntValue) val2;
        int num1;
        int num2;
        num1 = int1.getVal();
        num2 = int2.getVal();
        switch(op) {
            case 1:
                return new IntValue(num1 + num2);
            case 2:
                return new IntValue(num1 - num2);
            case 3:
                return new IntValue(num1 * num2);
            case 4:
                if(num2 == 0) {
                    throw new DivisionByZeroException("ERROR: Division by zero!\n");
                }
                else return new IntValue(num1 / num2);
            default:
                throw new OperandException("ERROR: The operand is not one of { +, -, *, / }!\n");
        }
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if(type2.equals(new IntType())) {
                return new IntType();
            }
            else {
                throw new TypeException("ERROR: The second operand is not an integer!\n");
            }
        }
        else {
            throw new TypeException("ERROR: The first operand is not an integer!\n");
        }
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + opStr + " " + exp2.toString();
    }

    @Override
    public IExp deepCopy() {
        return new ArithExp(opStr, exp1.deepCopy(), exp2.deepCopy());
    }
}
