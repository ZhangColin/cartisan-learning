package com.cartisan.lambda.utils;

import java.util.function.Consumer;

/**
 * @author colin
 */
public interface PresentOrElseHandler<T extends Object> {
    void presentOrElseHandle(Consumer<? super T> action, Runnable emptyAction);
}
