package com.codetaylor.mc.athenaeum.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V>
    extends LinkedHashMap<K, V> {

  public static <K, V> LRUCache<K, V> create(int capacity) {

    return new LRUCache<>(capacity);
  }

  private int maxSize;

  public LRUCache(int capacity) {

    super(capacity, 0.75f, true);
    this.maxSize = capacity;
  }

  @Override
  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {

    return this.size() > this.maxSize;
  }

  private LRUCache() {
    //
  }

}
