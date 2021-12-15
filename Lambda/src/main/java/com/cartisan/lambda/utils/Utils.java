package com.cartisan.lambda.utils;

/**
 * @author colin
 */
public class Utils {
    public static ThrowExceptionFunction isTrue(boolean condition) {
        return (errorMessage)->{
            if (condition) {
                throw new RuntimeException(errorMessage);
            }
        };
    }

    public static BranchHandle isTrueOrFalse(boolean condition) {
        return ((trueHandle, falseHandle) -> {
            if (condition) {
                trueHandle.run();
            }
            else {
                falseHandle.run();
            }
        });
    }

    public static PresentOrElseHandler<?> isBlankOrNoBlank(String str) {
        return (consumer, runnable)->{
            if (str == null || str.length() == 0) {
                runnable.run();
            }
            else {
                consumer.accept(str);
            }
        };
    }
}
