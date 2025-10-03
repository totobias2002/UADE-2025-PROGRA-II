package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.LinkedListADT;

public class StaticLinkedListADT implements LinkedListADT {

    private final int MAX_SIZE;
    private int[] elements;
    private int[] next;
    private int head;
    private int free;
    private int size;

    public StaticLinkedListADT(int capacity) {
        this.MAX_SIZE = capacity;
        this.elements = new int[capacity];
        this.next = new int[capacity];
        this.head = -1;
        this.size = 0;

        for (int i = 0; i < capacity - 1; i++) {
            next[i] = i + 1;
        }
        next[capacity - 1] = -1;
        free = 0;
    }

    @Override
    public void add(int value) {
        insert(size, value);
    }

    @Override
    public void insert(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Indice fuera de rango");
        }
        if (free == -1) {
            throw new IllegalStateException("Lista llena");
        }

        int newIndex = free;
        free = next[free];
        elements[newIndex] = value;

        if (index == 0) {
            next[newIndex] = head;
            head = newIndex;
        } else {
            int prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = next[prev];
            }
            next[newIndex] = next[prev];
            next[prev] = newIndex;
        }
        size++;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Indice fuera de rango");
        }

        int removeIndex;
        if (index == 0) {
            removeIndex = head;
            head = next[head];
        } else {
            int prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = next[prev];
            }
            removeIndex = next[prev];
            next[prev] = next[removeIndex];
        }


        next[removeIndex] = free;
        free = removeIndex;
        size--;
    }

    @Override
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Indice fuera de rango");
        }

        int current = head;
        for (int i = 0; i < index; i++) {
            current = next[current];
        }
        return elements[current];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
