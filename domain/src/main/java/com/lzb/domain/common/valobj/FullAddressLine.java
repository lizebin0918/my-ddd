package com.lzb.domain.common.valobj;

import java.beans.ConstructorProperties;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class FullAddressLine implements Serializable {

    private final String addressLine1;
    private final String addressLine2;
}
