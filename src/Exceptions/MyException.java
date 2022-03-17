package Exceptions;

public abstract class MyException extends Exception {
    protected MyException(String message) {
        super(message);
    }
}
