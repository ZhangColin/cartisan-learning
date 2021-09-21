package com.sso.filter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sso.config.RsaKeyProperties;
import com.sso.pojo.RolePojo;
import com.sso.pojo.UserPojo;
import com.sso.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author colin
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final RsaKeyProperties prop;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        this.authenticationManager = authenticationManager;
        this.prop = prop;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            final UserPojo userPojo = new ObjectMapper().readValue(request.getInputStream(), UserPojo.class);

            final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userPojo.getUsername(), userPojo.getPassword());

            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                Map resultMap = new HashMap();
                resultMap.put("code", HttpServletResponse.SC_UNAUTHORIZED);
                resultMap.put("msg", "用户名或密码错误！");
                out.write(new ObjectMapper().writeValueAsString(resultMap));
                out.flush();
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final UserPojo userPojo = new UserPojo();
        userPojo.setUsername(authResult.getName());
        userPojo.setRoles(authResult.getAuthorities().stream().map(grantedAuthority -> {
            final RolePojo rolePojo = new RolePojo();
            rolePojo.setRoleName(grantedAuthority.getAuthority());
            return rolePojo;
        }).collect(Collectors.toList()));
        String token = JwtUtils.generateTokenExpireInMinutes(userPojo, prop.getPrivateKey(), 24*60);
        response.addHeader("Authorization", "Bearer "+ token);
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            final PrintWriter writer = response.getWriter();
            Map resultMap = new HashMap();
            resultMap.put("code", HttpServletResponse.SC_OK);
            resultMap.put("msg", "认证通过");

            writer.write(new ObjectMapper().writeValueAsString(resultMap));
            writer.flush();
            writer.close();
        } catch (Exception outEx) {
            outEx.printStackTrace();
        }
    }
}
