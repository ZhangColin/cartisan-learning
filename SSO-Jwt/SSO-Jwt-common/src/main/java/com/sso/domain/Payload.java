package com.sso.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author colin
 */
@Data
public class Payload<T> {
    private String id;
    private T userInfo;
    private Date expiration;
}
