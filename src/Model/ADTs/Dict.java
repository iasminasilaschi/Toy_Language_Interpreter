package Model.ADTs;

import java.util.*;

public class Dict<T1, T2> implements IDict<T1, T2> {
    Map<T1, T2> dict;

    public Dict() {
        dict = new HashMap<>();
    }

    @Override
    public void add(T1 key, T2 elem) {
        dict.put(key, elem);
    }

    @Override
    public void update(T1 key, T2 elem) {
        dict.put(key, elem);
    }

    @Override
    public void remove(T1 key) {
        dict.remove(key);
    }

    @Override
    public Map<T1, T2> getContent() {
        return dict;
    }

    @Override
    public void setContent(Map<T1, T2> new_dict) {
        dict = new_dict;
    }

    @Override
    public T2 lookUp(T1 key) {
        return dict.get(key);
    }

    @Override
    public boolean isDefined(T1 id) {
        return dict.containsKey(id);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (T1 key   : dict.keySet()) {
            T2 elem = dict.get(key);
            str.append(key.toString()).append(" --> ").append(elem.toString());
            str.append("\n");
        }
        return str.toString();
    }

    public Dict<T1,T2> deepCopy() {
        Dict<T1,T2> newDict = new Dict<>();
        newDict.dict.putAll(dict);
        return newDict;
    }

    public Set<T1> keys() {
        return dict.keySet();
    }

    public Collection<T2> elems() {
        return dict.values();
    }
}
