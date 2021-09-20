package file.management.com.controller;

import com.mongodb.MongoClient;
import file.management.com.dao.UserDAO;
import file.management.com.model.User;

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

@WebServlet(name = "signup", urlPatterns = {"/signup"})
@MultipartConfig
public class SignUpController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = null;
        boolean check = false;

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        if(username == null || password == null || name == null){
            message = "username, password, name cannot be empty";
        } else {
            MongoClient mongo = (MongoClient) req.getServletContext().getAttribute("MONGO_CLIENT");
            UserDAO userDAO = new UserDAO(mongo);

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setCreatedAt(Timestamp.from(Instant.now()));
            user.setUpdatedAt(Timestamp.from(Instant.now()));
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
