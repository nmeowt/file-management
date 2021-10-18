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

        if (username == null && password == null) {
            message = "username and password cannot be empty";
        } else {
            UserDAO userDAO = new UserDAO();
            check = userDAO.checkLogin(username, password);
            message = (check) ? "login successfully" : "wrong username or password";
        }
        ResponseAlert.response(resp, message, check);
    }
}
