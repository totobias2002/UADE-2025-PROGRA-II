package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.PriorityQueueADT;

public class StaticPriorityQueueADT implements PriorityQueueADT {

    private final int MAX_SIZE;
    private int[] elements;
    private int[] priorities;
    private int size;

    public StaticPriorityQueueADT(int capacity) {
        this.MAX_SIZE = capacity;
        this.elements = new int[capacity];
        this.priorities = new int[capacity];
        this.size = 0;
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return elements[0];
    }

    @Override
    public int getPriority() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return priorities[0];
    }

    @Override
    public void add(int value, int priority) {
        if (size == MAX_SIZE) {
            throw new IllegalStateException("La cola está llena");
        }

        int i;
        for (i = size - 1; i >= 0; i--) {
            if (priorities[i] >= priority) break;
            elements[i + 1] = elements[i];
            priorities[i + 1] = priorities[i];
        }

        elements[i + 1] = value;
        priorities[i + 1] = priority;
        size++;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }
        // Eliminamos el primer elemento
        for (int i = 0; i < size - 1; i++) {
            elements[i] = elements[i + 1];
            priorities[i] = priorities[i + 1];
        }
        size--;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Opcional: tamaño actual de la cola
    public int getSize() {
        return size;
    }
}

