package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.StackADT;
import org.uade.structure.implementation.common.Node;

public class DynamicStackADT implements StackADT {

    private Node top;
    private int size;

    public DynamicStackADT() {
        top = null;
        size = 0;
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return top.value;
    }

    @Override
    public void add(int value) {
        Node newNode = new Node(value);
        newNode.next = top;
        top = newNode;
        size++;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        top = top.next;
        size--;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}

