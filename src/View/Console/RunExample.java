package View.Console;

import Controller.Controller;
import Exceptions.MyException;
import View.Console.Command;

import java.io.IOException;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String key, String desc, Controller controller_) {
        super(key, desc);
        controller = controller_;
    }

    @Override
    public void execute() {
        try {
            controller.allStep(true);
        } catch (MyException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
