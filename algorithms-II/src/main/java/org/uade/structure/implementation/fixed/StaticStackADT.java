package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.StackADT;

public class StaticStackADT implements StackADT {

    private final int MAX_SIZE;
    private int[] elements;
    private int top; // índice del último elemento

    public StaticStackADT(int capacity) {
        this.MAX_SIZE = capacity;
        this.elements = new int[capacity];
        this.top = -1;
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return elements[top];
    }

    @Override
    public void add(int value) {
        if (top >= MAX_SIZE - 1) {
            throw new IllegalStateException("La pila está llena");
        }
        elements[++top] = value;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        top--;
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }


    public int getSize() {
        return top + 1;
    }
}
