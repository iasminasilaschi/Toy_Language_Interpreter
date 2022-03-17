package Model.ADTs;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDict<T1, T2> {
    void add(T1 key, T2 elem);
    void update(T1 key, T2 elem);
    void remove(T1 key);
    Map<T1, T2> getContent();
    void setContent(Map<T1, T2> new_dict);
    Set<T1> keys();
    Collection<T2> elems();
    T2 lookUp(T1 key);
    boolean isDefined(T1 id);
    String toString();
    IDict<T1,T2> deepCopy();
}
