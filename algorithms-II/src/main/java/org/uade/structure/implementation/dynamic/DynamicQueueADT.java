package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.QueueADT;
import org.uade.structure.implementation.common.Node;

public class DynamicQueueADT implements QueueADT {

    private Node front;
    private Node rear;
    private int size;

    public DynamicQueueADT() {
        front = null;
        rear = null;
        size = 0;
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return front.value;
    }

    @Override
    public void add(int value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.next = newNode;
        }
        rear = newNode;
        size++;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }
        front = front.next;
        if (front == null) {
            rear = null;
        }
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

