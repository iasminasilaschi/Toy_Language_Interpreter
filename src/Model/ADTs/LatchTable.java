package Model.ADTs;

import java.util.Map;

public class LatchTable extends Dict<Integer, Integer> {
    int new_free_location = 0;

    public int get_new_free_location() {
        return new_free_location;
    }

    void update_new_free_location() {
        new_free_location += 1;
    }

    public void add_new(Integer value) {
        synchronized (this) {
            this.update_new_free_location();
            super.add(new_free_location, value);
        }
    }

    @Override
    public void add(Integer id, Integer value) {
        synchronized (this) {
            super.add(id, value);
        }
    }

    @Override
    public void update(Integer id, Integer value) {
        synchronized (this) {
            super.update(id, value);
        }
    }

    @Override
    public Integer lookUp(Integer id) {
        synchronized (this) {
            return super.lookUp(id);
        }
    }

    @Override
    public boolean isDefined(Integer id) {
        synchronized (this) {
            return super.isDefined(id);
        }
    }

    @Override
    public LatchTable deepCopy() {
        LatchTable new_latch_table = new LatchTable();
        new_latch_table.setContent(dict);
        return new_latch_table;
    }
}
