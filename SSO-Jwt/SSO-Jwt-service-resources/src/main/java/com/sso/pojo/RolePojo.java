package com.sso.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author colin
 */
@Data
public class RolePojo implements GrantedAuthority {
    private Integer id;
    private String roleName;
    private String roleDesc;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return null;
    }
}
