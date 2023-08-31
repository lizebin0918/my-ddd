package com.lzb.domain.order.valobj;

import java.io.Serializable;

/**
 * 姓名<br/>
 * Created on : 2023-08-31 12:53
 * @author lizebin
 */
public record FullName(String firstName, String lastName) implements Serializable {


}
