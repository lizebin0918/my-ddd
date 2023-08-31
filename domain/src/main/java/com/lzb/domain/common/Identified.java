package com.lzb.domain.common;

import java.util.Collection;


public interface Identified {
    static boolean isDuplicated(Collection<? extends Identified> collection) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }

        long count = collection.stream().map(Identified::getIdentifier).distinct().count();
        return count != collection.size();
    }

    String getIdentifier();
}