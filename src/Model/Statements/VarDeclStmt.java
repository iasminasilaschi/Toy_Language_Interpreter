package Model.Statements;

import Exceptions.DeclarationException;
import Exceptions.MyException;
import Model.ADTs.IDict;
import Model.PrgState;
import Model.Types.IType;
import Model.Values.IValue;

public class VarDeclStmt implements IStmt {
    String name;
    IType type;

    public VarDeclStmt(String name_, IType type_) {
        name = name_;
        type = type_;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        if(symTable.isDefined(name)) {
            throw new DeclarationException("ERROR: This variable is already declared!\n");
        }
        symTable.add(name, type.defaultValue());
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        typeEnv.add(name, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type.deepCopy());
    }
}
