package com.lzb.domain.order.aggregation.valobj;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;


/**
 * 订单地址实体<br/>
 * Created on : 2023-08-31 12:52
 * @author lizebin
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@Jacksonized
@Builder(toBuilder = true)
public class OrderAddress implements Serializable {

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
            FullName fullName,
            FullAddressLine fullAddressLine,
            String email,
            String phoneNumber,
            String country) {
        this.fullName = fullName;
        this.fullAddressLine = fullAddressLine;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

}
