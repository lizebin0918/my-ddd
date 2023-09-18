package com.lzb.domain.order.valobj;

import java.beans.ConstructorProperties;
import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class FullAddressLine implements Serializable {

    private final String addressLine1;
    private final String addressLine2;
    @ConstructorProperties({"addressLine1", "addressLine2"})
    public FullAddressLine(String addressLine1, String addressLine2) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
    }
}
