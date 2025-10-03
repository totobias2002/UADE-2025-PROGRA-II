package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.SetADT;

public class DynamicSetADT implements SetADT {

    private Node head;

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    public DynamicSetADT() {
        head = null;
    }

    @Override
    public boolean exist(int value) {
        Node current = head;
        while (current != null) {
            if (current.value == value) return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public int choose() {
        if (head == null) throw new IllegalStateException("El conjunto está vacío");


        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }


        int randomIndex = (int) (Math.random() * count);
        current = head;
        for (int i = 0; i < randomIndex; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public void add(int value) {
        if (!exist(value)) {
            Node newNode = new Node(value);
            newNode.next = head;
            head = newNode;
        }
    }

    @Override
    public void remove(int element) {
        if (head == null) return;

        if (head.value == element) {
            head = head.next;
            return;
        }

        Node current = head;
        while (current.next != null && current.next.value != element) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
