package org.ibs.cdx.gode.codegen.velocity.util;

public class Triple<L,M,R> extends Pair<L,R> {

    public M getMiddle() {
        return middle;
    }

    private final M middle;

    public Triple(L left, M middle, R right) {
        super(left, right);
        this.middle = middle;
    }
}
