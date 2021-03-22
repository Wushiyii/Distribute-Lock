package com.wushiyii.model;

import java.io.Serializable;

public class Pair<L, R> implements Serializable {
    
    private static final long serialVersionUID = 4954918890077043841L;

    private L left;
    private R right;

    public static <L, R> Pair<L, R> of(L left, R right) {
        Pair<L, R> pair = new Pair<>();
        pair.setLeft(left);
        pair.setRight(right);
        return pair;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public final L getKey() {
        return this.getLeft();
    }

    public R getValue() {
        return this.getRight();
    }

    @Override
    public int hashCode() {
        return (this.getKey() == null ? 0 : this.getKey().hashCode()) ^ (this.getValue() == null ? 0 : this.getValue().hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return left.equals(pair.left) && right.equals(pair.right);
    }

    public String toString() {
        return "" + '(' + this.getLeft() + ',' + this.getRight() + ')';
    }

    public String toString(String format) {
        return String.format(format, this.getLeft(), this.getRight());
    }
}