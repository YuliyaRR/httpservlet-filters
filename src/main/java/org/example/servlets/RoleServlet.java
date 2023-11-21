package org.example.servlets;

import com.google.gson.Gson;
import org.example.dto.RoleDto;
import org.example.service.IRoleService;
import org.example.service.RoleService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/roles")
public class RoleServlet extends HttpServlet {

    private IRoleService roleService;
    private Gson gson;

    @Override
    public void init() {
        this.roleService = new RoleService();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        RoleDto role = roleService.getRole(id);
        sendResp(resp, role, 200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RoleDto fromRequest = getFromRequest(req);
        RoleDto role = roleService.createRole(fromRequest);
        sendResp(resp, role, 201);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RoleDto fromRequest = getFromRequest(req);
        RoleDto role = roleService.updateRole(fromRequest);
        sendResp(resp, role, 200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.parseLong(req.getParameter("id"));
        boolean doesRoleDelete = roleService.deleteRole(id);
        int code = doesRoleDelete ? 200 : 404;

        resp.setStatus(code);
        resp.setContentType("application/json");
    }

    private void sendResp(HttpServletResponse response, Object o, int code) throws IOException {
        String role = gson.toJson(o);
        response.getWriter().write(role);
        response.setStatus(code);
        response.setContentType("application/json");
    }

    private RoleDto getFromRequest(HttpServletRequest request) {
        String res = request.getAttribute("body").toString();
        return gson.fromJson(res, RoleDto.class);
    }
}
