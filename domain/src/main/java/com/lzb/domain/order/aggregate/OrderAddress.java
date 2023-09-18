package com.lzb.domain.order.aggregate;

import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.lzb.domain.common.aggregate.BaseEntity;
import com.lzb.domain.order.valobj.FullAddressLine;
import com.lzb.domain.order.valobj.FullName;
import lombok.Getter;
import lombok.NonNull;


/**
 * 订单地址实体<br/>
 * Created on : 2023-08-31 12:52
 * @author lizebin
 */
@Getter
public class OrderAddress extends BaseEntity<OrderAddress> {

    @NonNull
    private final FullName fullName;

    @NonNull
    private final FullAddressLine fullAddressLine;

    /**
     * 邮箱
     */
    @NonNull
    private String email;

    /**
     * 电话
     */
    @NonNull
    private String phoneNumber;

    /**
     * 国家
     */
    @NonNull
    private String country;

    @JsonCreator
    public OrderAddress(Long id,
            @NonNull FullName fullName,
            @NonNull FullAddressLine fullAddressLine,
            @NonNull String email,
            @NonNull String phoneNumber,
            @NonNull String country) {
        super(id);
        this.fullName = fullName;
        this.fullAddressLine = fullAddressLine;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

    /**
     * 更新姓名
     * @param fullName
     */
    void updateFullName(FullName fullName) {
        Assert.notBlank(fullName.getFirstName(), "firstName is null");
        this.fullName = fullName;
    }
}
