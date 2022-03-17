package Model.ADTs;

import Model.Values.StringValue;

import java.io.BufferedReader;

public class FileTable extends Dict<StringValue, BufferedReader> {

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(StringValue key: dict.keySet()) {
            str.append(key.toString()).append("\n");
        }
        return str.toString();
    }
}
