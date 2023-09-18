package com.lzb.domain.order.valobj;

import java.beans.ConstructorProperties;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/**
 * 姓名<br/>
 * Created on : 2023-08-31 12:53
 * @author lizebin
 */
@Getter
@EqualsAndHashCode
public class FullName implements Serializable {

    private final String firstName;
    private final String lastName;

    @ConstructorProperties({"firstName", "lastName"})
    public FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
