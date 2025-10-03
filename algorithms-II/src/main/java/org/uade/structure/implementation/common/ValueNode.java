package org.uade.structure.implementation.common;

public class ValueNode {
    public int value;
    public ValueNode next;

    public ValueNode(int value) {
        this.value = value;
        this.next = null;
    }
}