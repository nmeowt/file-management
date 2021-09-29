package file.management.com.controller;

import file.management.com.dao.UserDAO;
import file.management.com.model.User;
import file.management.com.utils.ResponseAlert;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(name = "profile", urlPatterns = {"/profile"})
@MultipartConfig
public class UpdateProfileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("user_id"));
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(userId);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String context = user.toJson();
        out.print(context);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = null;
        boolean check = false;
        int userId = Integer.parseInt(req.getParameter("user_id"));
        String username = req.getParameter("username");
        String name = req.getParameter("name");
        if (userId == 0 || username == null || name == null) {
            message = "user id, username, name cannot be empty";
        } else {
            User user = new User();
            user.setUserId(userId);
            user.setUsername(username);
            user.setName(name);
            user.setModifiedAt(new Date());

            UserDAO userDAO = new UserDAO();
            userDAO.update(user);
            check = true;
            message = "updated user successfully";
        }

        ResponseAlert.response(resp, message, check);
    }
}
