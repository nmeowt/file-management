package file.management.com.servlet;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import file.management.com.constants.Constants;
import file.management.com.dao.UserDAO;
import file.management.com.model.User;
import file.management.com.utils.HashPassword;
import org.bson.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@WebServlet(name = "signup", urlPatterns = {"/signup"})
@MultipartConfig
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = null;
        boolean check = false;

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String hashedPass = HashPassword.hash(password);
        String name = req.getParameter("name");
        if (username == null || password == null || name == null) {
            message = "username, password, name cannot be empty";
        } else {
            UserDAO userDAO = new UserDAO();
            User user = new User();
            user.setUsername(username);
            user.setPassword(hashedPass);
            user.setName(name);
            user.setCreatedAt(new Date());
            user.setModifiedAt(new Date());
            userDAO.create(user);
            check = true;
            message = "create new user successfully";
        }

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String context = "{" +
                "\"status\": \"" + check + "\"," +
                "\"message\": \"" + message + "\"," +
                "}";
        out.print(context);
        out.flush();
    }
}
