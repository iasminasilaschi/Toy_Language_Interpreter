package Model.ADTs;

import Model.Values.IValue;

import java.util.ArrayList;

public class Heap extends Dict<Integer, IValue> {
    Integer first_free = 1;
    ArrayList<Boolean> all_free;

    public Integer getFree() {
        setFree();
        for (int i = 1; i <= dict.size() + 1; i++) {
            if(all_free.get(i)) {
                first_free = i;
                return first_free;
            }
        }
        return first_free;
    }

    public void setFree() {
        all_free = new ArrayList<>();
        for (int i = 0; i <= dict.size() + 1; i++) {
            all_free.add(i, true);
        }
        all_free.set(0, false);
        for (Integer key : dict.keySet()) {
            all_free.set(key, false);
        }
    }
}
