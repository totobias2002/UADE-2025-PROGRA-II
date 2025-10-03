package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.SimpleDictionaryADT;
import org.uade.structure.definition.SetADT;

public class StaticSimpleDictionaryADT implements SimpleDictionaryADT {

    private final int MAX_SIZE;
    private int[] keys;
    private int[] values;
    private int size;

    public StaticSimpleDictionaryADT(int capacity) {
        this.MAX_SIZE = capacity;
        this.keys = new int[capacity];
        this.values = new int[capacity];
        this.size = 0;
    }

    @Override
    public void add(int key, int value) {
        for (int i = 0; i < size; i++) {
            if (keys[i] == key) {
                values[i] = value;
                return;
            }
        }

        if (size >= MAX_SIZE) {
            throw new IllegalStateException("La estructura está llena");
        }

        keys[size] = key;
        values[size] = value;
        size++;
    }

    @Override
    public void remove(int key) {
        for (int i = 0; i < size; i++) {
            if (keys[i] == key) {
                for (int j = i; j < size - 1; j++) {
                    keys[j] = keys[j + 1];
                    values[j] = values[j + 1];
                }
                size--;
                return;
            }
        }
    }

    @Override
    public int get(int key) {
        for (int i = 0; i < size; i++) {
            if (keys[i] == key) return values[i];
        }
        throw new IllegalArgumentException("La clave no existe");
    }

    @Override
    public SetADT getKeys() {
        StaticSetADT set = new StaticSetADT(MAX_SIZE);
        for (int i = 0; i < size; i++) {
            set.add(keys[i]);
        }
        return set;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }
}
