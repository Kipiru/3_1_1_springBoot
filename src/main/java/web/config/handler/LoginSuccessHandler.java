package web.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> roles = new HashSet<>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin/users");
        } else if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/user");
        }
    }
}