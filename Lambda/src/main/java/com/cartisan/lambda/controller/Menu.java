package com.cartisan.lambda.controller;

import lombok.Data;

import java.util.List;

/**
 * @author colin
 */
@Data
public class Menu {
    private Integer id;
    private Integer parentId;
    private String name;

    private List<Menu> children;

    public Menu(Integer id, Integer parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}
