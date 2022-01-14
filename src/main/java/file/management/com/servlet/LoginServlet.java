package file.management.com.servlet;

import file.management.com.dao.UserDAO;
import file.management.com.model.User;
import file.management.com.utils.HashPassword;
import file.management.com.utils.JWT;
import file.management.com.utils.ResponseAlert;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "login", urlPatterns = {"/login"})
@MultipartConfig
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String hashedPass = HashPassword.hash(password);
        String jwt = null;

        UserDAO userDAO = new UserDAO();
        try {
            User user = userDAO.checkLogin(username, hashedPass);
            jwt = JWT.createJWT(String.valueOf(user.getUserId()));
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        ResponseAlert.loginResponse(resp, jwt);
    }
}
