package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.BinaryTreeADT;

public class StaticBinaryTreeADT implements BinaryTreeADT {

    private final int MAX_SIZE;
    private int[] elements;
    private int[] left;
    private int[] right;
    private int root;
    private int free;
    private int size;

    public StaticBinaryTreeADT(int capacity) {
        this.MAX_SIZE = capacity;
        this.elements = new int[capacity];
        this.left = new int[capacity];
        this.right = new int[capacity];
        this.root = -1;
        this.size = 0;


        for (int i = 0; i < capacity; i++) {
            left[i] = -1;
            right[i] = i + 1;
        }
        right[capacity - 1] = -1;
        free = 0;
    }

    @Override
    public int getRoot() {
        if (root == -1) throw new IllegalStateException("El árbol está vacío");
        return elements[root];
    }

    @Override
    public BinaryTreeADT getLeft() {
        if (root == -1 || left[root] == -1) return null;
        StaticBinaryTreeADT subtree = new StaticBinaryTreeADT(MAX_SIZE);
        subtree.copyFromIndex(this, left[root]);
        return subtree;
    }

    @Override
    public BinaryTreeADT getRight() {
        if (root == -1 || right[root] == -1) return null;
        StaticBinaryTreeADT subtree = new StaticBinaryTreeADT(MAX_SIZE);
        subtree.copyFromIndex(this, right[root]);
        return subtree;
    }

    @Override
    public void add(int value) {
        if (free == -1) throw new IllegalStateException("Árbol lleno");

        int newIndex = free;
        free = right[free];
        elements[newIndex] = value;
        left[newIndex] = -1;
        right[newIndex] = -1;

        if (root == -1) {
            root = newIndex;
        } else {
            int current = root;
            while (true) {
                if (value < elements[current]) {
                    if (left[current] == -1) {
                        left[current] = newIndex;
                        break;
                    } else {
                        current = left[current];
                    }
                } else {
                    if (right[current] == -1) {
                        right[current] = newIndex;
                        break;
                    } else {
                        current = right[current];
                    }
                }
            }
        }
        size++;
    }

    @Override
    public void remove(int value) {
        root = removeRecursive(root, value);
    }

    private int removeRecursive(int node, int value) {
        if (node == -1) return -1;

        if (value < elements[node]) {
            left[node] = removeRecursive(left[node], value);
        } else if (value > elements[node]) {
            right[node] = removeRecursive(right[node], value);
        } else {

            if (left[node] == -1 && right[node] == -1) { // hoja
                freeNode(node);
                return -1;
            } else if (left[node] == -1) {
                int temp = right[node];
                freeNode(node);
                return temp;
            } else if (right[node] == -1) {
                int temp = left[node];
                freeNode(node);
                return temp;
            } else {

                int successor = findMin(right[node]);
                elements[node] = elements[successor];
                right[node] = removeRecursive(right[node], elements[successor]);
            }
        }
        return node;
    }

    private int findMin(int node) {
        while (left[node] != -1) node = left[node];
        return node;
    }

    private void freeNode(int node) {
        right[node] = free;
        free = node;
        size--;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    private void copyFromIndex(StaticBinaryTreeADT source, int index) {
        if (index == -1) return;
        this.add(source.elements[index]);
        copyFromIndex(source, source.left[index]);
        copyFromIndex(source, source.right[index]);
    }
}

