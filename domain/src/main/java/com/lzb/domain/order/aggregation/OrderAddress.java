package com.lzb.domain.order.aggregation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lzb.domain.order.aggregation.valobj.FullAddressLine;
import com.lzb.domain.order.aggregation.valobj.FullName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


/**
 * 订单地址实体<br/>
 * Created on : 2023-08-31 12:52
 * @author lizebin
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@Builder(toBuilder = true)
public class OrderAddress {

    private FullName fullName;

    private FullAddressLine fullAddressLine;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phoneNumber;

    /**
     * 国家
     */
    private String country;

    @JsonCreator
    public OrderAddress(
            @NonNull FullName fullName,
            @NonNull FullAddressLine fullAddressLine,
            @NonNull String email,
            @NonNull String phoneNumber,
            @NonNull String country) {
        this.fullName = fullName;
        this.fullAddressLine = fullAddressLine;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

}
