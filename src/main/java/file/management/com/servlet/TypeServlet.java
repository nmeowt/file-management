package file.management.com.servlet;

import com.mongodb.MongoClient;
import file.management.com.dao.TypeDAO;
import file.management.com.model.Type;
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
import java.util.List;

@WebServlet("/type")
@MultipartConfig
public class TypeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TypeDAO typeDAO = new TypeDAO();
        List<Type> types = typeDAO.getAll();
        ResponseAlert.getStringType(resp, types);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String message = null;
        boolean check = false;
        if (name == null) {
            message = "name cannot be empty";
        } else {
            Type type = new Type();
            type.setName(name);
            TypeDAO typeDAO = new TypeDAO();
            typeDAO.create(type);
            check = true;
            message = "inserted type successfully";
        }
        ResponseAlert.response(resp, message, check);
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
            Type type = new Type();
            type.setTypeId(Integer.parseInt(id));
            type.setName(name);
            TypeDAO typeDAO = new TypeDAO();
            typeDAO.update(type);
            check = true;
            message = "updated type - " + id + " successfully";
        }
        ResponseAlert.response(resp, message, check);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String message = null;
        boolean check = false;
        if (id == null) {
            message = "id cannot be empty";
        } else {
            Type type = new Type();
            type.setTypeId(Integer.parseInt(id));
            TypeDAO typeDAO = new TypeDAO();
            typeDAO.delete(type);
            check = true;
            message = "deleted type - " + id + " successfully";
        }
        ResponseAlert.response(resp, message, check);
    }
}
