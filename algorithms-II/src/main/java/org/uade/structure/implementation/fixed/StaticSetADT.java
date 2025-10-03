package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.SetADT;

public class StaticSetADT implements SetADT {

    private final int MAX_SIZE;
    private int[] elements;
    private int size;

    public StaticSetADT(int capacity) {
        this.MAX_SIZE = capacity;
        this.elements = new int[capacity];
        this.size = 0;
    }

    @Override
    public boolean exist(int value) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == value) return true;
        }
        return false;
    }

    @Override
    public int choose() {
        if (size == 0) throw new IllegalStateException("El conjunto está vacío");
        int randomIndex = (int) (Math.random() * size);
        return elements[randomIndex];
    }

    @Override
    public void add(int value) {
        if (exist(value)) return;

        if (size >= MAX_SIZE) {
            throw new IllegalStateException("El conjunto está lleno");
        }

        elements[size] = value;
        size++;
    }

    @Override
    public void remove(int value) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == value) {

                for (int j = i; j < size - 1; j++) {
                    elements[j] = elements[j + 1];
                }
                size--;
                return;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    public int[] getAllElements() {
        int[] copy = new int[size];
        for (int i = 0; i < size; i++) {
            copy[i] = elements[i];
        }
        return copy;
    }

    public int getSize() {
        return size;
    }
}
