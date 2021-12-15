package com.cartisan.lambda.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.cartisan.lambda.utils.Utils.isTrueOrFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * @author colin
 */
public class UtilsTest {
    @Test
    public void throwException() {
        assertThrows(RuntimeException.class, ()->Utils.isTrue(true).throwMessage("error"));
    }

    @Test
    public void branchHandle() {
        isTrueOrFalse(true)
                .trueOrFalseHandle(()->{
                    System.out.println("true!!!");
                }, ()->{
                    System.out.println("false!!!");
                });
    }

    @Test
    void isBlankOrNoBlank() {
        Utils.isBlankOrNoBlank("hello")
                .presentOrElseHandle(System.out::println, ()->{
                    System.out.println("Empty");
                });
    }
}
