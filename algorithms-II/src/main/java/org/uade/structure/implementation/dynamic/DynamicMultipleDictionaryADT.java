package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.MultipleDictionaryADT;
import org.uade.structure.definition.SetADT;
import org.uade.structure.implementation.common.KeyNode;
import org.uade.structure.implementation.common.ValueNode;

public class DynamicMultipleDictionaryADT implements MultipleDictionaryADT {

    private KeyNode keys;

    public DynamicMultipleDictionaryADT() {
        this.keys = null;
    }

    @Override
    public void add(int key, int value) {
        KeyNode keyNode = findKey(key);

        if (keyNode == null) {
            keyNode = new KeyNode(String.valueOf(key));
            keyNode.next = keys;
            keys = keyNode;
        }

        ValueNode newValue = new ValueNode(value);
        newValue.next = keyNode.value;
        keyNode.value = newValue;
    }

    @Override
    public void remove(int key) {
        if (keys == null) return;

        if (Integer.parseInt(keys.key) == key) {
            keys = keys.next;
            return;
        }

        KeyNode current = keys;
        while (current.next != null && Integer.parseInt(current.next.key) != key) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    @Override
    public void remove(int key, int value) {
        KeyNode keyNode = findKey(key);
        if (keyNode == null) return;

        ValueNode current = keyNode.value;
        ValueNode prev = null;

        while (current != null && current.value != value) {
            prev = current;
            current = current.next;
        }

        if (current != null) {
            if (prev == null) {
                keyNode.value = current.next;
            } else {
                prev.next = current.next;
            }
        }
    }

    @Override
    public int[] get(int key) {
        KeyNode keyNode = findKey(key);
        if (keyNode == null) {
            throw new IllegalArgumentException("La clave no existe");
        }

        int count = 0;
        ValueNode current = keyNode.value;
        while (current != null) {
            count++;
            current = current.next;
        }

        int[] values = new int[count];
        current = keyNode.value;
        int i = 0;
        while (current != null) {
            values[i++] = current.value;
            current = current.next;
        }

        return values;
    }

    @Override
    public SetADT getKeys() {
        throw new UnsupportedOperationException("Falta implementar usando SetADT");
    }

    @Override
    public boolean isEmpty() {
        return keys == null;
    }

    // 🔹 Método auxiliar para encontrar una clave
    private KeyNode findKey(int key) {
        KeyNode current = keys;
        while (current != null) {
            if (Integer.parseInt(current.key) == key) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
}
