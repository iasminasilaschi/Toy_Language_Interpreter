package Model.Expressions;

import Exceptions.MyException;
import Exceptions.OperandException;
import Exceptions.TypeException;
import Exceptions.TypeMismatchException;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IValue;
import Model.Values.IntValue;

public class RelationalExp implements IExp {
    private String operator;
    private IExp exp1;
    private IExp exp2;

    public RelationalExp(String operator_, IExp exp1_, IExp exp2_) {
        operator = operator_;
        exp1 = exp1_;
        exp2 = exp2_;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap heap) throws MyException {
        IValue val1, val2;
        val1 = exp1.eval(symTable, heap);
        val2 = exp2.eval(symTable, heap);
        IntValue int1 = (IntValue) val1;
        IntValue int2 = (IntValue) val2;
        int num1;
        int num2;
        num1 = int1.getVal();
        num2 = int2.getVal();
        return switch (operator) {
            case "<=" -> new BoolValue(num1 <= num2);
            case "<" -> new BoolValue(num1 < num2);
            case ">=" -> new BoolValue(num1 >= num2);
            case ">" -> new BoolValue(num1 > num2);
            case "==" -> new BoolValue(num1 == num2);
            case "!=" -> new BoolValue(num1 != num2);
            default -> throw new OperandException("ERROR: The operand is not one of { <=, <, >=, >, ==, != }!\n");
        };
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if(type2.equals(new IntType())) {
                return new BoolType();
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
    public IExp deepCopy() {
        return new RelationalExp(operator, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + operator + " " + exp2.toString();
    }
}
