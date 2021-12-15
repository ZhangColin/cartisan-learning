package com.cartisan.lambda.utils;

/**
 * @author colin
 */
public interface BranchHandle {
    void trueOrFalseHandle(Runnable trueHandle, Runnable falseHandle);
}
