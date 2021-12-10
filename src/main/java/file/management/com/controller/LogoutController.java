package file.management.com.controller;

import file.management.com.utils.ResponseAlert;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "logout", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = "no user found";
        boolean check = false;
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
            message = "logout successfully";
            check = true;
        }

        ResponseAlert.response(resp, message, check);
    }
}
