package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.SetADT;
import org.uade.structure.definition.SimpleDictionaryADT;
import org.uade.structure.implementation.common.KeyNode;

public class DynamicSimpleDictionaryADT implements SimpleDictionaryADT {

    private KeyNode keys;

    public DynamicSimpleDictionaryADT() {
        this.keys = null;
    }

    @Override
    public void add(int key, int value) {
        KeyNode keyNode = findKey(key);

        if (keyNode == null) {
            keyNode = new KeyNode(String.valueOf(key));
            keyNode.singleValue = value;
            keyNode.next = keys;
            keys = keyNode;
        } else {
            keyNode.singleValue = value;
        }
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
    public int get(int key) {
        KeyNode keyNode = findKey(key);
        if (keyNode == null || keyNode.singleValue == null) {
            throw new IllegalArgumentException("La clave no existe");
        }
        return keyNode.singleValue;
    }

    @Override
    public SetADT getKeys() {
        throw new UnsupportedOperationException("Falta implementar usando SetADT");
    }

    @Override
    public boolean isEmpty() {
        return keys == null;
    }

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
