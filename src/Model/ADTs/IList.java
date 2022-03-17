package Model.ADTs;

import Exceptions.MyException;

public interface IList<T> {
    void add(T elem);
    T getAt(int pos) throws MyException;
    T removeAt(int pos) throws MyException;
    boolean isEmpty();
    void clear();
    int getSize();
    String toString();
}
