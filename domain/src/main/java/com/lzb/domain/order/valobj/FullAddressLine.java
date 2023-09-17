package com.lzb.domain.order.valobj;

import java.io.Serializable;

import lombok.Value;

@Value
public class FullAddressLine implements Serializable {
    String addressLine1;
    String addressLine2;
}
