package com.sourcetrace.eses.multitenancy;

import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class IdentityHashSet<T> {
    private final Set<T> set;

    public IdentityHashSet() {
        set = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public boolean contains(T element) {
        return set.contains(element);
    }

    public void add(T element) {
        set.add(element);
    }

    public void remove(T element) {
        set.remove(element);
    }
}
