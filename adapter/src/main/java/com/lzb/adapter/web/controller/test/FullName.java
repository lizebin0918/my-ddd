package com.lzb.adapter.web.controller.test;

import com.lzb.component.utils.validation.ValidatorUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FullName {

    @NotBlank(message = "firstName不能为空")
    private final String firstName;
    private final String lastName;

    public FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        ValidatorUtils.validate(this);
    }
}