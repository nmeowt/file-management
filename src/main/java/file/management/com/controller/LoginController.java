package file.management.com.controller;

import com.mongodb.MongoClient;
import file.management.com.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
@MultipartConfig
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String message = null;
        boolean check = false;

        if(username == null && password == null){
            message = "username and password cannot be empty";
        } else {
            MongoClient mongo = (MongoClient) req.getServletContext().getAttribute("MONGO_CLIENT");
            UserDAO userDAO = new UserDAO(mongo);
            check = userDAO.checkLogin(username, password);
            message = (check) ? "login successfully" : "wrong username or password";
        }

        System.out.println(message);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String context = "{" +
                "\"status\": \"" + check + "\"," +
                "\"message\": \"" + message + "\"," +
                "}";
        out.print(context);
        out.flush();
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000/");
        resp.setHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, token, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    }
}
