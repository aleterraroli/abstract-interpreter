package it.univr.pl;

import it.univr.pl.value.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Mem {

    private final Map<String, ExpValue<?>> values = new HashMap<>();

    public boolean contains(String id) {
        return values.containsKey(id) && values.get(id) != null;
    }

    public ExpValue<?> getValue(String id) {
        return values.get(id);
    }

    public void updateValue(String id, ExpValue<?> v) {
        values.put(id, v);
    }

    public void add(String id, ExpValue<?> v) {
        values.put(id, v);
    }

    public Set<String> getKeys() {
        return values.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for (String var : values.keySet())
            sb.append(var).append(":").append(values.get(var)).append(" ");
        sb.append("}");
        return sb.toString();
    }
}