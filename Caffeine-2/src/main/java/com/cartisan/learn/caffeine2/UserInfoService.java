package com.cartisan.learn.caffeine2;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @author colin
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")
public class UserInfoService {
    private HashMap<Integer, UserInfo> userInfoMap = new HashMap<>();

    @CachePut(key = "#userInfo.id")
    public UserInfo addUserInfo(UserInfo userInfo) {
        log.info("create");

        userInfoMap.put(userInfo.getId(), userInfo);

        return userInfo;
    }

    @Cacheable(key = "#id")
    public UserInfo getByName(Integer id){
        log.info("get");
        return userInfoMap.get(id);
    }

    @CachePut(key = "#userInfo.id")
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

        return oldUserInfo;
    }

    @CacheEvict(key = "#id")
    public void deleteById(Integer id) {
        log.info("delete");
        userInfoMap.remove(id);
    }
}
