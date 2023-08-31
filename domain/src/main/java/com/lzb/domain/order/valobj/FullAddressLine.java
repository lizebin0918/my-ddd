package com.lzb.domain.order.valobj;

import java.io.Serializable;

public record FullAddressLine(String addressLine1, String addressLine2) implements Serializable {
}
