package com.sso.service.impl;

import com.sso.pojo.RolePojo;
import com.sso.pojo.UserPojo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.sso.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author colin
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (s.equals( "colin")) {
            final UserPojo userPojo = new UserPojo();
            userPojo.setId(1);
            userPojo.setUsername("colin");
            userPojo.setPassword("$2a$10$l225lSjAR37R0PcCjTmHGu.TDlZSc8Mfb3bzc.mUEinHpZOoXhVPC");
            userPojo.setStatus(1);

            final ArrayList<RolePojo> roles = new ArrayList<>();
            final RolePojo rolePojo = new RolePojo();
            rolePojo.setRoleName("Leader");
            rolePojo.setRoleDesc("Leader");
            roles.add(rolePojo);
            userPojo.setRoles(roles);

            return userPojo;
        }

        return null;
    }
}
