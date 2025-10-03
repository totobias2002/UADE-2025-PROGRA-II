package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.MultipleDictionaryADT;
import org.uade.structure.definition.SetADT;

public class StaticMultipleDictionaryADT implements MultipleDictionaryADT {

    private final int MAX_KEYS;
    private int[] keys;
    private int[][] values;
    private int[] sizes;
    private int size;

    public StaticMultipleDictionaryADT(int capacity, int maxValuesPerKey) {
        this.MAX_KEYS = capacity;
        this.keys = new int[capacity];
        this.values = new int[capacity][maxValuesPerKey];
        this.sizes = new int[capacity];
        this.size = 0;
    }

    @Override
    public void add(int key, int value) {
        int index = findKeyIndex(key);

        if (index == -1) {
            if (size >= MAX_KEYS) {
                throw new IllegalStateException("Se alcanzó la cantidad máxima de claves");
            }
            keys[size] = key;
            values[size][0] = value;
            sizes[size] = 1;
            size++;
        } else {
            if (sizes[index] >= values[index].length) {
                throw new IllegalStateException("Se alcanzó la cantidad máxima de valores para esta clave");
            }
            values[index][sizes[index]] = value;
            sizes[index]++;
        }
    }

    @Override
    public void remove(int key) {
        int index = findKeyIndex(key);
        if (index == -1) return;

        for (int i = index; i < size - 1; i++) {
            keys[i] = keys[i + 1];
            values[i] = values[i + 1];
            sizes[i] = sizes[i + 1];
        }
        size--;
    }

    @Override
    public void remove(int key, int value) {
        int index = findKeyIndex(key);
        if (index == -1) return;

        int count = sizes[index];
        int[] vals = values[index];
        boolean removed = false;

        for (int i = 0; i < count; i++) {
            if (!removed && vals[i] == value) {
                removed = true;
            }
            if (removed && i < count - 1) {
                vals[i] = vals[i + 1];
            }
        }

        if (removed) sizes[index]--;
    }

    @Override
    public int[] get(int key) {
        int index = findKeyIndex(key);
        if (index == -1) {
            throw new IllegalArgumentException("La clave no existe");
        }

        int[] result = new int[sizes[index]];
        System.arraycopy(values[index], 0, result, 0, sizes[index]);
        return result;
    }

    @Override
    public SetADT getKeys() {
        StaticSetADT set = new StaticSetADT(MAX_KEYS);
        for (int i = 0; i < size; i++) {
            set.add(keys[i]);
        }
        return set;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int findKeyIndex(int key) {
        for (int i = 0; i < size; i++) {
            if (keys[i] == key) return i;
        }
        return -1;
    }
}
