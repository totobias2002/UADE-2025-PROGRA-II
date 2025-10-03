package org.uade.structure.implementation.common;

import org.uade.structure.implementation.common.ValueNode;

public class KeyNode {
    public String key;
    public ValueNode values;    // para MultipleDictionaryADT
    public Integer singleValue; // para SimpleDictionaryADT
    public KeyNode next;
    public ValueNode value;

    public KeyNode(String key) {
        this.key = key;
        this.next = null;
        this.values = null;
        this.singleValue = null;
    }
}
