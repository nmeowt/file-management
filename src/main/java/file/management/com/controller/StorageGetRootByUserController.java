package file.management.com.controller;

import com.mongodb.MongoClient;
import file.management.com.dao.StorageDAO;
import file.management.com.dao.TypeDAO;
import file.management.com.dao.UserDAO;
import file.management.com.dto.StorageDTO;
import file.management.com.model.Storage;
import file.management.com.model.Type;
import file.management.com.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "storage_get_root_by_user", urlPatterns = {"/storage/get_root_by_user"})
public class StorageGetRootByUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String owner = req.getParameter("owner");
        MongoClient mongo = (MongoClient) req.getServletContext()
                .getAttribute("MONGO_CLIENT");
        StorageDAO storageDAO = new StorageDAO(mongo);
        TypeDAO typeDAO = new TypeDAO(mongo);
        UserDAO userDAO = new UserDAO(mongo);
        List<Storage> storages = storageDAO.getFolderByUser(owner);
        List<StorageDTO> storagesDTO = StorageController.convertDTO(typeDAO, userDAO, storages);
        String context = "";
        context = StorageController.getString(context, storagesDTO);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + context + "]");
        out.flush();
    }
}
