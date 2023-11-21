package org.example.filters;

import org.example.entity.Role;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.service.UserService;

import javax.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class RoleUpdateFilter implements Filter {
    private final UserService userService = new UserService();
    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        String userLogin = (String) session.getAttribute("user_login");
        Set<Role> roles = (Set<Role>) session.getAttribute("roles");

        if(userLogin != null && roles != null) {
            Optional<User> userByUsername = userService.getUserByUsername(userLogin);

            if (userByUsername.isPresent()) {
                User user = userByUsername.get();
                Set<Role> roleDB = user.getRole();

                if (!Objects.equals(roles, roleDB)) {
                    session.setAttribute("roles", roleDB);
                }
            }
        } else {
            throw new UserNotFoundException("No information about user in session");
        }
    }

    @Override
    public void destroy() {

    }
}
