package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.QueueADT;

public class StaticQueueADT implements QueueADT {

    private final int MAX_SIZE;
    private int[] elements;
    private int front;
    private int rear;
    private int size;

    public StaticQueueADT(int capacity) {
        this.MAX_SIZE = capacity;
        this.elements = new int[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return elements[front];
    }

    @Override
    public void add(int value) {
        if (size == MAX_SIZE) {
            throw new IllegalStateException("La cola está llena");
        }
        elements[rear] = value;
        rear = (rear + 1) % MAX_SIZE;
        size++;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }
        front = (front + 1) % MAX_SIZE;
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
