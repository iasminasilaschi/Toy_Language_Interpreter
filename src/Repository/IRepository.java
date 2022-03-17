package Repository;

import Exceptions.MyException;
import Model.PrgState;

import java.util.List;

public interface IRepository {
    void addMainPrg();
    void logPrgStateExec(PrgState state) throws MyException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);
}
