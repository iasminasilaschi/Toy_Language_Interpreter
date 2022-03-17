package Model.ADTs;

public interface IStack<T> {
    T pop();
    void push(T elem);
    boolean isEmpty();
    String toString();
}
