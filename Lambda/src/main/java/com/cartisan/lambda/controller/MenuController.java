package com.cartisan.lambda.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author colin
 */
@RestController
@RequestMapping("/menus")
public class MenuController {
    @GetMapping
    public List<Menu> getMenus() {
        List<Menu> menus = Arrays.asList(
                new Menu(1,0,"根节点"),
                new Menu(2,1,"子节点1"),
                new Menu(3,2,"子节点1.1"),
                new Menu(4,2,"子节点1.2"),
                new Menu(5,2,"根节点1.3"),
                new Menu(6,1,"根节点2"),
                new Menu(7,6,"根节点2.1"),
                new Menu(8,6,"根节点2.2"),
                new Menu(9,7,"根节点2.2.1"),
                new Menu(10,7,"根节点2.2.2"),
                new Menu(11,1,"根节点3"),
                new Menu(12,11,"根节点3.1")
        );

        return getChildMenus(0, menus);
    }

    private List<Menu> getChildMenus(Integer parentId, List<Menu> all) {
        return all.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(getChildMenus(menu.getId(), all)))
                .collect(Collectors.toList());
    }
}
