package Model.Statements;

import Exceptions.*;
import Model.ADTs.Heap;
import Model.ADTs.IDict;
import Model.Expressions.IExp;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.IValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    IExp exp;
    String var_name;

    public ReadFileStmt(IExp exp_, String varName_) {
        exp = exp_;
        var_name = varName_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap heap = state.getHeapTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        if(!(symTbl.isDefined(var_name))) {
            throw new DeclarationException("ERROR: This variable name is not defined!\n");
        }
        IValue val1 = symTbl.lookUp(var_name);
        if(!(val1.getType().equals(new IntType()))) {
            throw new TypeMismatchException("ERROR: The variable is not of type int!\n");
        }
        IValue val2 = exp.eval(symTbl, heap);
        if(!(val2.getType().equals(new StringType()))) {
            throw new TypeMismatchException("ERROR: The variable is not of type string!\n");
        }
        StringValue file = (StringValue) val2;
        if(!(fileTable.isDefined(file))) {
            throw new FileDeclaredException("ERROR: The file is not declared!\n");
        }
        BufferedReader reader = fileTable.lookUp(file);
        IntValue readVal;
        try {
            String readStr = reader.readLine();
            if(readStr == null) {
                readVal = new IntValue();
            }
            else {
                readVal = new IntValue(Integer.parseInt(readStr));
            }
            symTbl.update(var_name, readVal);
            return null;
        }
        catch (IOException ioe) {
            throw new FileException("ERROR: Cannot read file!\n");
        }
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookUp(var_name);
        IType typeExp = exp.typeCheck(typeEnv);
        if(typeVar.equals(new IntType())) {
            if(typeExp.equals(new StringType())) {
                return typeEnv;
            }
            else {
                throw new TypeMismatchException("ERROR: The variable is not of type string!\n");
            }
        }
        else {
            throw new TypeMismatchException("ERROR: The variable is not of type int!\n");
        }
    }

    @Override
    public String toString() {
        return "readFile(" + exp.toString() + ", " + var_name + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), var_name);
    }
}
