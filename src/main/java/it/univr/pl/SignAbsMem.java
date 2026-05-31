package it.univr.pl;

import it.univr.pl.value.SignValue;
import java.util.HashMap;
import java.util.Map;

public class SignAbsMem {
    private final Map<String, SignValue> map;

    public SignAbsMem() {
        this.map = new HashMap<>();
    }

    public SignAbsMem(SignAbsMem other) {
        this.map = new HashMap<>(other.map);
    }

    public void add(String id, SignValue value) {
        map.put(id, value);
    }

    public void update(String id, SignValue value) {
        if(map.containsKey(id))
            map.put(id, value);
    }

    public SignValue get(String id) {
        return map.getOrDefault(id, SignValue.BOTTOM);
    }

    public void lub(SignAbsMem other) {
        for (String key : this.map.keySet()) {
            if (other.map.containsKey(key)) {
                SignValue myVal = this.map.get(key);
                SignValue otherVal = other.map.get(key);
                this.map.put(key, myVal.lub(otherVal));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SignAbsMem)) return false;
        return this.map.equals(((SignAbsMem) o).map);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public String toString() {
        return map.toString();
    }

}
