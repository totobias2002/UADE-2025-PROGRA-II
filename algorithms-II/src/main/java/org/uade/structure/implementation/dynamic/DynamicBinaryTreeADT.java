package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.BinaryTreeADT;
import org.uade.structure.implementation.common.BinaryTreeNode;

public class DynamicBinaryTreeADT implements BinaryTreeADT {

    private BinaryTreeNode root;

    public DynamicBinaryTreeADT() {
        this.root = null;
    }

    @Override
    public int getRoot() {
        if (isEmpty()) {
            throw new IllegalStateException("El árbol está vacío");
        }
        return root.value;
    }

    @Override
    public BinaryTreeADT getLeft() {
        if (isEmpty() || root.left == null) {
            return null;
        }
        DynamicBinaryTreeADT leftTree = new DynamicBinaryTreeADT();
        leftTree.root = root.left;
        return leftTree;
    }

    @Override
    public BinaryTreeADT getRight() {
        if (isEmpty() || root.right == null) {
            return null;
        }
        DynamicBinaryTreeADT rightTree = new DynamicBinaryTreeADT();
        rightTree.root = root.right;
        return rightTree;
    }

    @Override
    public void add(int value) {
        root = insertRec(root, value);
    }

    private BinaryTreeNode insertRec(BinaryTreeNode node, int value) {
        if (node == null) {
            return new BinaryTreeNode(value);
        }
        if (value < node.value) {
            node.left = insertRec(node.left, value);
        } else if (value > node.value) {
            node.right = insertRec(node.right, value);
        }
        return node;
    }

    @Override
    public void remove(int value) {
        root = removeRec(root, value);
    }

    private BinaryTreeNode removeRec(BinaryTreeNode node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = removeRec(node.left, value);
        } else if (value > node.value) {
            node.right = removeRec(node.right, value);
        } else {

            if (node.left == null && node.right == null) {
                return null;
            }

            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            node.value = minValue(node.right);
            node.right = removeRec(node.right, node.value);
        }
        return node;
    }

    private int minValue(BinaryTreeNode node) {
        int min = node.value;
        while (node.left != null) {
            min = node.left.value;
            node = node.left;
        }
        return min;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }
}
