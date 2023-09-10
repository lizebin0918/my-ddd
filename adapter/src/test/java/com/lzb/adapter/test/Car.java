package com.lzb.adapter.test;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * <br/>
 * Created on : 2023-09-10 18:23
 * @author lizebin
 */
@Data
public class Car {

    @NotNull(message = "manufacturer不能为空")
    private String manufacturer;

    @NotNull(message = "licensePlate不能为空")
    @Size(min = 2, max = 14)
    private String licensePlate;

    @Min(2)
    private int seatCount;

    private List<@NotNull String> parts = new ArrayList<>();

    public void addPart(String part) {
        parts.add( part );
    }
    public Car(String manufacturer, String licencePlate, int seatCount) {
        this.manufacturer = manufacturer;
        this.licensePlate = licencePlate;
        this.seatCount = seatCount;
    }

}
