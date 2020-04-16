package com.behdavar.backservices.common.model.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LimitHashMap<K, V> extends LinkedHashMap<K, V> {

    private final int MAX_ENTRIES;

    public LimitHashMap(int maxEntries) {
        MAX_ENTRIES = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_ENTRIES;
    }
}
