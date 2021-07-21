package com.cartisan.learn.caffeine2;

import org.springframework.web.bind.annotation.*;

/**
 * @author colin
 */
@RequestMapping("/userInfo")
@RestController
public class UserInfoController {
    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/{id}")
    public Object getUserInfo(@PathVariable Integer id) {
        final UserInfo userInfo = userInfoService.getByName(id);
        if (userInfo == null) {
            return "没有该用户";
        }
        return userInfo;
    }

    @PostMapping
    public Object createUserInfo(@RequestBody UserInfo userInfo) {
        userInfoService.addUserInfo(userInfo);

        return "SUCCESS";
    }

    @PutMapping
    public Object updateUserInfo(@RequestBody UserInfo userInfo) {
        final UserInfo newUserInfo = userInfoService.updateUserInfo(userInfo);
        if (newUserInfo == null) {
            return "不存在该用户";
        }

        return "SUCCESS";
    }

    @DeleteMapping("/{id}")
    public Object deleteUserInfo(@PathVariable Integer id) {
        userInfoService.deleteById(id);

        return "SUCCESS";
    }
}
