package com.lzb.adapter.test;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>
 * Created on : 2023-09-10 18:23
 * @author lizebin
 */
public class CarTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("添加null")
    void should_() {
        Car car = new Car( "Morris", "DD-AB-123", 1 );
        car.addPart( null );

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate( car );

        System.out.println(constraintViolations.size());
        assertEquals( 2, constraintViolations.size());
    }

    @Test
    void manufacturerIsNull() {
        Car car = new Car( null, "DD-AB-123", 4 );

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate( car );

        assertEquals( 1, constraintViolations.size() );
        assertEquals( "manufacturer不能为空", constraintViolations.iterator().next().getMessage() );
    }

}
