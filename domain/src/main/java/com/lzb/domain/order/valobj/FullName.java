package com.lzb.domain.order.valobj;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.NonNull;
import lombok.Value;

/**
 * 姓名<br/>
 * Created on : 2023-08-31 12:53
 * @author lizebin
 */
@Value
public class FullName implements Serializable {

    String firstName;
    String lastName;

}
