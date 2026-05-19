package it.univr.pl;

import it.univr.pl.type.ExpType;
import it.univr.pl.type.TypeUtils;
import it.univr.pl.value.*;
import java.util.HashMap;
import java.util.Map;

public class Mem {

    private final Map<String, ExpValue<?>> values;
    private final Map<String, ExpType> types;

    public Mem() {
        values = new HashMap<>();
        types = new HashMap<>();
    }

    public Mem(Mem mem) {
        values = new HashMap<>(mem.values);
        types = new HashMap<>(mem.types);
    }

    public boolean contains(String id) {
        return values.containsKey(id) && values.get(id) != null;
    }

    public ExpValue<?> getValue(String id) {
        return values.get(id);
    }

    public ExpType getType(String id) {
        return types.get(id);
    }

    public void updateValue(String id, ExpValue<?> value) {
        values.put(id, value);
    }

    public void add(String id, ExpValue<?> value) {
        values.put(id, value);
        types.put(id, TypeUtils.fromValue(value));
    }

    public void add(String id, ExpType type) {
        values.put(id, null);
        types.put(id, type);
    }

    public void updateValues(Mem other) {
        for (String idOther : other.values.keySet())
            for (String id : values.keySet())
                if (idOther.equals(id))
                    updateValue(id, other.values.get(idOther));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for (String var : values.keySet())
            sb.append(var).append("[").append(types.get(var).getName()).append("]:").append(values.get(var)).append(" ");
        sb.append("}");
        return sb.toString();
    }
}