package org.example.servlets;

import com.google.gson.Gson;
import org.example.dto.UserDto;
import org.example.service.IUserService;
import org.example.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private IUserService userService;
    private Gson gson;

    @Override
    public void init() {
        this.userService = new UserService();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto user = userService.getUser(id);
        sendResp(resp, user, 200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto fromRequest = getFromRequest(req);
        UserDto user = userService.createUser(fromRequest);
        sendResp(resp, user, 201);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto fromRequest = getFromRequest(req);
        UserDto user = userService.updateUser(fromRequest);
        sendResp(resp, user, 200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        Long id = Long.parseLong(req.getParameter("id"));
        boolean doesUserDelete = userService.deleteUser(id);
        int code = doesUserDelete ? 200 : 404;

        resp.setStatus(code);
        resp.setContentType("application/json");
    }

    private void sendResp(HttpServletResponse response, Object o, int code) throws IOException {
        String user1 = gson.toJson(o);
        response.getWriter().write(user1);
        response.setStatus(code);
        response.setContentType("application/json");
    }

    private UserDto getFromRequest(HttpServletRequest request) {
        String res = request.getAttribute("body").toString();
        return gson.fromJson(res, UserDto.class);
    }
}
