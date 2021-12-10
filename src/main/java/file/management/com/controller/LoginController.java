package file.management.com.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import file.management.com.constants.Constants;
import file.management.com.dao.UserDAO;
import file.management.com.utils.ResponseAlert;
import org.bson.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "login", urlPatterns = {"/login"})
@MultipartConfig
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String message = null;
        boolean check = false;
        HttpSession session = req.getSession();

        if (username == null && password == null) {
            message = "username and password cannot be empty";
        } else {
            if (session.getAttribute("user") == null) {
                UserDAO userDAO = new UserDAO();
                check = userDAO.checkLogin(username, password);
                if (check) {
                    session.setAttribute("user", username);
                    message = "login successfully";
                } else {
                    message = "wrong username or password";
                }
            } else {
                message = "user has logged in";
            }
        }
        ResponseAlert.response(resp, message, check);
    }
}
