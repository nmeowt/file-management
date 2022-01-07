package file.management.com.servlet;

import file.management.com.dao.UserDAO;
import file.management.com.model.User;
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
    private static final String collection = "user";
    private static final int jwtTimeToLive = 800000;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String jwt = null;

        UserDAO userDAO = new UserDAO();
        try {
            User user = userDAO.checkLogin(username, password);
            System.out.println(user.getName().equals(null));
            jwt = JWT.createJWT(
                    String.valueOf(user.getUserId()),
                    jwtTimeToLive
            );
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        ResponseAlert.loginResponse(resp, jwt);
    }
}
