package org.example.filters;

import org.example.entity.User;
import org.example.exception.AuthException;
import org.example.exception.UserNotFoundException;
import org.example.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

public class AuthFilter implements Filter {

    private final UserService userService = new UserService();

    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String authorizationHeader = req.getHeader("Authorization");

        if(authorizationHeader.isBlank() || Objects.isNull(authorizationHeader)) {
            throw new AuthException("You aren't authorized");
        }

        String authorization = authorizationHeader.split(" ")[1];
        Base64.Decoder decoder = Base64.getDecoder();
        String[] data = new String(decoder.decode(authorization)).split(":");

        if(data.length == 0) {
            throw new AuthException("You aren't authorized");
        }

        Optional<User> userByUsername = userService.getUserByUsername(data[0]);

        if(userByUsername.isPresent()) {
            User user = userByUsername.get();
            if (user.getPassword().equals(data[1])) {
                session.setAttribute("roles", user.getRole());
                session.setAttribute("user_login", user.getName());
                chain.doFilter(request, response);
            } else {
                throw new AuthException("Invalid credentials");
            }
        } else {
            session.invalidate();
            throw new UserNotFoundException("You didn't found in the system");
        }
    }

    @Override
    public void destroy() {

    }
}
