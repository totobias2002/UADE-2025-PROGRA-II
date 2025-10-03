package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.PriorityQueueADT;
import org.uade.structure.implementation.common.PriorityNode;

public class DynamicPriorityQueueADT implements PriorityQueueADT {

    private PriorityNode front;
    private int size;

    public DynamicPriorityQueueADT() {
        front = null;
        size = 0;
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola con prioridad está vacía");
        }
        return front.value;
    }

    @Override
    public int getPriority() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola con prioridad está vacía");
        }
        return front.priority;
    }

    @Override
    public void add(int value, int priority) {
        PriorityNode newNode = new PriorityNode(value, priority);

        if (isEmpty() || priority < front.priority) {

            newNode.next = front;
            front = newNode;
        } else {

            PriorityNode current = front;
            while (current.next != null && current.next.priority <= priority) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }

        size++;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola con prioridad está vacía");
        }
        front = front.next;
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
