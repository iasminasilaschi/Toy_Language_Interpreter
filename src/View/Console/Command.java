package View.Console;

import Exceptions.MyException;

public abstract class Command {
    private final String key;
    private final String description;

    public Command(String key_, String description_) {
        key = key_;
        description = description_;
    }

    public abstract void execute() throws MyException;

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
