package com.sso.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sso.config.RsaKeyProperties;
import com.sso.domain.Payload;
import com.sso.pojo.UserPojo;
import com.sso.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author colin
 */
public class TokenVerifyFilter extends BasicAuthenticationFilter {
    private final RsaKeyProperties prop;

    public TokenVerifyFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        super(authenticationManager);
        this.prop = prop;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            chain.doFilter(request, response);

            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            final PrintWriter writer = response.getWriter();
            Map resultMap = new HashMap();
            resultMap.put("code", HttpServletResponse.SC_FORBIDDEN);
            resultMap.put("msg", "请登录");

            writer.write(new ObjectMapper().writeValueAsString(resultMap));
            writer.flush();
            writer.close();
        }
        else {
            final String token = authorization.replace("Bearer ", "");
            final Payload<UserPojo> payload = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), UserPojo.class);

            final UserPojo userInfo = payload.getUserInfo();

            if (userInfo != null) {
                final UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(userInfo.getUsername(), null, userInfo.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authResult);
                chain.doFilter(request, response);
            }
        }
    }
}
