package com.lzb.domain.order.aggregate;

import java.beans.ConstructorProperties;

import cn.hutool.core.lang.Assert;
import com.lzb.domain.common.aggregate.BaseEntity;
import com.lzb.domain.order.valobj.FullAddressLine;
import com.lzb.domain.order.valobj.FullName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 订单地址实体<br/>
 * Created on : 2023-08-31 12:52
 * @author lizebin
 */
@Getter
// 方便测试构造
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public class OrderAddress extends BaseEntity<OrderAddress> {

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

    protected OrderAddress(long id) {
        super(id);
    }

    @ConstructorProperties({"id", "fullName", "fullAddressLine", "email", "phoneNumber", "country"})
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
