package edu.macalester.comp124.mymap;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple implementation of a hashtable.
 *
 * @author shilad
 *
 * @param <K> Class for keys in the table.
 * @param <V> Class for values in the table.
 */
public class MyMap <K, V> {
	private static final int INITIAL_SIZE = 4;
	
	/** The table is package-protected so that the unit test can examine it. */
	List<MyEntry<K, V>> [] buckets;
	
	/** Number of unique entries (e.g. keys) in the table */
	private int numEntries = 0;
	
	/** Threshold that determines when the table should expand */
	private double loadFactor = 0.75;
	
	/**
	 * Initializes the data structures associated with a new hashmap.
	 */
	public MyMap() {
		buckets = newArrayOfEntries(INITIAL_SIZE);
	}
	
	/**
	 * Returns the number of unique entries (e.g. keys) in the table.
	 * @return the number of entries.
	 */
	public int size() {
		return numEntries;
	}
	
	/**
	 * Adds a new key, value pair to the table.
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		expandIfNecessary();
		MyEntry entry = new MyEntry(key, value);
        int position = key.hashCode() % buckets.length;
        //System.out.println("This is the hash code for entry: " + entry.getKey() + " which is "+ entry.hashCode() + " which mod is " + entry.hashCode()%buckets.length);
        List<MyEntry<K,V>> testBucket = buckets[position];
        for (MyEntry eachEntry : testBucket) {
            Object existingKey = eachEntry.getKey();
            if (existingKey.equals(key)) {
                eachEntry.setValue((V)entry.getValue());
                return;
            }
        }
        testBucket.add(entry);
        numEntries++;


		// TODO: Store the key.
	}
	
	/**
	 * Returns the value associated with the specified key, or null if it
	 * doesn't exist.
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {
		int position = key.hashCode()%buckets.length;
        V value = null;
        for (MyEntry eachEntry : buckets[position]) {
            Object testKey = eachEntry.getKey();
            if (testKey.equals(key)) {
                value = (V)eachEntry.getValue();
            }
        }
        return value;
	}
	
	/**
	 * Expands the table to double the size, if necessary.
	 */
	private void expandIfNecessary() {
		if (numEntries/buckets.length > loadFactor) {
            List<MyEntry<K, V>> [] oldBuckets = buckets;
            buckets = newArrayOfEntries(2*oldBuckets.length);
            for (int i = 0; i< oldBuckets.length; i++) {
                for (MyEntry eachEntry : oldBuckets[i]) {
                    K key = (K)eachEntry.getKey();
                    V value = (V)eachEntry.getValue();
                    put(key, value);
                }
            }
        }
	}
	
	/**
	 * Returns an array of the specified size, each
	 * containing an empty linked list that can be
	 * filled with MyEntry objects.
	 * 
	 * @param capacity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<MyEntry<K,V>>[] newArrayOfEntries(int capacity) {
		List<MyEntry<K, V>> [] entries = (List<MyEntry<K,V>> []) (
				java.lang.reflect.Array.newInstance(LinkedList.class, capacity));
		for (int i = 0; i < entries.length; i++) {
			entries[i] = new LinkedList();
		}
		return entries;
	}
	
}
