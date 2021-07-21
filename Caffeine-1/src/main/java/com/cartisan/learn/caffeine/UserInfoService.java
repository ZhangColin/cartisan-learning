package com.cartisan.learn.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;

/**
 * @author colin
 */
@Slf4j
@Service
public class UserInfoService {
    private final Cache<String, Object> caffeineCache;
    private HashMap<Integer, UserInfo> userInfoMap = new HashMap<>();

    public UserInfoService(Cache<String, Object> caffeineCache) {
        this.caffeineCache = caffeineCache;
    }

    public void addUserInfo(UserInfo userInfo) {
        log.info("create");

        userInfoMap.put(userInfo.getId(), userInfo);

        // 写入缓存
        caffeineCache.put(String.valueOf(userInfo.getId()), userInfo);
    }

    public UserInfo getByName(Integer id){
        // 先从缓存读取
        caffeineCache.getIfPresent(id);

        UserInfo userInfo = (UserInfo) caffeineCache.asMap().get(String.valueOf(id));
        if (userInfo != null) {
            return userInfo;
        }

        // 如果缓存中不存在，则从库中查找
        log.info("get");
        userInfo = userInfoMap.get(id);

        // 如果用户信息不为空，则加入缓存
        if (userInfo != null) {
            caffeineCache.put(String.valueOf(userInfo.getId()), userInfo);
        }

        return userInfo;
    }

    public UserInfo updateUserInfo(UserInfo userInfo) {
        log.info("update");

        if (!userInfoMap.containsKey(userInfo.getId())) {
            return null;
        }

        final UserInfo oldUserInfo = userInfoMap.get(userInfo.getId());
        if (!StringUtils.isEmpty(oldUserInfo.getAge())) {
            oldUserInfo.setAge(oldUserInfo.getAge());
        }
        if (!StringUtils.isEmpty(oldUserInfo.getName())) {
            oldUserInfo.setName(oldUserInfo.getName());
        }
        if (!StringUtils.isEmpty(oldUserInfo.getSex())) {
            oldUserInfo.setSex(oldUserInfo.getSex());
        }

        userInfoMap.put(oldUserInfo.getId(), oldUserInfo);

        caffeineCache.put(String.valueOf(oldUserInfo.getId()), oldUserInfo);

        return oldUserInfo;
    }

    public void deleteById(Integer id) {
        log.info("delete");
        userInfoMap.remove(id);

        caffeineCache.asMap().remove(String.valueOf(id));
    }
}
