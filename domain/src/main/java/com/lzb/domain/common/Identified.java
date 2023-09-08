package com.lzb.domain.common;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;


public interface Identified<T extends BaseEntity<T>> {

    @JsonIgnore
    default boolean isDuplicated() {
        var collection = getCollection();
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        long count = collection.stream().map(BaseEntity::getId).distinct().count();
        return count != collection.size();
    }

    @JsonIgnore
    Collection<T> getCollection();
}