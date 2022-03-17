package Repository;

import Exceptions.FileException;
import Exceptions.MyException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileRepository implements IRepository {

    String logFilePath;
    PrgState program;
    List<PrgState> programsList;

    public FileRepository(PrgState program_, String logFilePath_) {
        logFilePath = logFilePath_;
        programsList = new ArrayList<>();
        program = program_;
        addMainPrg();
    }

    @Override
    public void addMainPrg() {
        programsList.add(program);
    }

    public java.util.List<PrgState> getPrgList() {
        return programsList;
    }

    public void setPrgList(List<PrgState> newList) {
        programsList = newList;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyException {
        PrintWriter writer;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e) {
            throw new FileException("ERROR: file exception, creating a file writer on the given file path failed\n");
        }
        writer.write(state.toString());
        writer.flush();
        writer.close();
    }
}