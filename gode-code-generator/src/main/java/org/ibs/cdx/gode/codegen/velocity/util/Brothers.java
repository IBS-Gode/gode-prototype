package org.ibs.cdx.gode.codegen.velocity.util;

public interface Brothers {

    public static  <L,R> Pair<L,R> twin(L left, R right){
        return new Pair<>(left,right);
    }

    public static  <L,M,R> Pair<L,R> triplets(L left, M middle,R right){
        return new Triple<>(left,middle,right);
    }
}
