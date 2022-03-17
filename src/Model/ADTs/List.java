package Model.ADTs;

import Exceptions.ListInvalidPositionException;
import Exceptions.MyException;

import java.util.ArrayList;

public class List<T> implements IList<T> {
    private java.util.List<T> list;

    public List() {
        list = new ArrayList<T>();
    }

    @Override
    public void add(T elem) {
        list.add(elem);
    }

    @Override
    public T getAt(int pos) throws MyException {
        if (pos < 0 || pos >= list.size()) {
            throw new ListInvalidPositionException("invalid position");
        }
        return list.get(pos);
    }

    @Override
    public T removeAt(int pos) throws MyException {
        if (pos < 0 || pos >= list.size()) {
            throw new ListInvalidPositionException("invalid position");
        }
        return list.remove(pos);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int index = 0; index < list.size() - 1; index++) {
            str.append((list.get(index)).toString());
            str.append("\n");
        }
        if (!list.isEmpty()) {
            str.append(list.get(list.size() - 1));
            str.append("\n");
        }
        return str.toString();
    }

    public List<T> deepCopy() {
        List<T> newList = new List<>();
        newList.list.addAll(list);
        return newList;
    }
}
