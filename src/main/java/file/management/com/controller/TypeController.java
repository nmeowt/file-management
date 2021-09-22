package file.management.com.controller;

import com.mongodb.MongoClient;
import file.management.com.dao.TypeDAO;
import file.management.com.model.Type;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/type")
@MultipartConfig
public class TypeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) req.getServletContext()
                .getAttribute("MONGO_CLIENT");
        TypeDAO typeDAO = new TypeDAO(mongo);
        List<Type> types = typeDAO.getAll();
        String context = "";
        for (Type type : types) {
            context += getString(type);
        }
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + context + "]");
        out.flush();
    }

    public String getString(Type type) {
        String context = "{" +
                "\"id\": \"" + type.getId() + "\"," +
                "\"name\": \"" + type.getName() + "\"" +
                "},";
        return context;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String message = null;
        boolean check = false;
        if (name == null) {
            message = "name cannot be empty";
        } else {
            MongoClient mongo = (MongoClient) req.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            Type type = new Type();
            type.setName(name);
            TypeDAO typeDAO = new TypeDAO(mongo);
            typeDAO.create(type);
            check = true;
            message = "inserted type successfully";
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String message = null;
        boolean check = false;
        if (id == null || name == null) {
            message = "id and name cannot be empty";
        } else {
            MongoClient mongo = (MongoClient) req.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            Type type = new Type();
//            type.setId(id);
            type.setName(name);
            TypeDAO typeDAO = new TypeDAO(mongo);
            typeDAO.update(type);
            check = true;
            message = "updated type - " + id + " successfully";
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String message = null;
        boolean check = false;
        if (id == null) {
            message = "id cannot be empty";
        } else {
            MongoClient mongo = (MongoClient) req.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            Type type = new Type();
//            type.setId(id);
            TypeDAO typeDAO = new TypeDAO(mongo);
            typeDAO.delete(type);
            check = true;
            message = "deleted type - " + id + " successfully";
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
