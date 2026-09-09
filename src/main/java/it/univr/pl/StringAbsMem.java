package it.univr.pl;

import it.univr.pl.value.StringValue;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StringAbsMem {
    private final Map<String, StringValue> env;

    public StringAbsMem() {
        this.env = new HashMap<>();
    }

    public StringAbsMem(StringAbsMem other) {
        this.env = new HashMap<>(other.env);
    }

    public StringValue get(String id) {
        return env.getOrDefault(id, StringValue.BOTTOM);
    }

    public void add(String id, StringValue val) {
        env.put(id, val);
    }

    public void update(String id, StringValue val) {
        env.put(id, val);
    }

    public Set<String> keySet() {
        return env.keySet();
    }

    public StringAbsMem lub(StringAbsMem other) {
        StringAbsMem result = new StringAbsMem();
        for (String key : this.env.keySet()) {
            if (other.env.containsKey(key)) {
                result.add(key, this.get(key).lub(other.get(key)));
            } else {
                result.add(key, this.get(key));
            }
        }
        for (String key : other.env.keySet()) {
            if (!result.env.containsKey(key)) {
                result.add(key, other.get(key));
            }
        }
        return result;
    }

    public void setAll(StringAbsMem other) {
        this.env.clear();
        this.env.putAll(other.env);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringAbsMem)) return false;
        return env.equals(((StringAbsMem) o).env);
    }

    @Override
    public String toString() {
        return env.toString();
    }
}