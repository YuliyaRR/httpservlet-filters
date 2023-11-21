package org.example.filters;

import org.example.entity.Role;
import org.example.entity.User;
import org.example.exception.AuthException;
import org.example.exception.InvalidUrlException;
import org.example.exception.NoAccessException;
import org.example.exception.UserNotFoundException;
import org.example.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Фильтр проверяет права доступа к эндпоинтам в зависимости от роли пользователя.
 * К сервлету /roles доступ имеет только админ,
 * к сервлету /users к методам put&delete&post доступ имеет только админ, к get - любой пользователь
 */
public class SecurityFilter implements Filter {
    private final UserService userService = new UserService();

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String reqUrl = req.getRequestURL().toString();

        if (reqUrl.endsWith("/roles")) {
            checkAdminRole(req);
        } else if (reqUrl.endsWith("/users")) {
            String reqMethod = req.getMethod();
            switch (reqMethod) {
                case "PUT", "POST", "DELETE" -> checkAdminRole(req);
            }
        } else {
            throw new InvalidUrlException("Such url does not exist");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void checkAdminRole(HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();

        Set<Role> roles = (Set<Role>) session.getAttribute("roles");

        if (Objects.isNull(roles)) {
            String userLogin = (String) session.getAttribute("user_login");
            if (Objects.nonNull(userLogin)) {
                Optional<User> userByUsername = userService.getUserByUsername(userLogin);
                if (userByUsername.isPresent()) {
                    User user = userByUsername.get();
                    roles = user.getRole();
                    session.setAttribute("roles", roles);
                } else {
                    throw new UserNotFoundException("User not found");
                }
            } else {
                throw new AuthException("Unregistered user");
            }
        }

        if (roles.stream().noneMatch(arr -> arr.getRole().equals("ADMIN"))) {
            throw new NoAccessException("You don't have permission for this request");
        }

    }
}
