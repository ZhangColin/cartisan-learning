package com.geektime.simplejdbcdemo;

import lombok.Builder;
import lombok.Data;

/**
 * @author colin
 */
@Data
@Builder
public class Foo {
    private Long id;
    private String bar;
}
