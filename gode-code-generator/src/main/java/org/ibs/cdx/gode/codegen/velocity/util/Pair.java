package org.ibs.cdx.gode.codegen.velocity.util;

public class Pair<L,R> {

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    private final L left;
    private final R right;
}
