package com.lzb.component.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateField {
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段中文文本
     */
    private String fieldDesc;
    /**
     * 旧值
     */
    private Object oldValue;
    /**
     * 新值
     */
    private Object newValue;
}