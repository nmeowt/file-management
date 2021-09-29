package file.management.com.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import file.management.com.constants.Constants;
import file.management.com.dao.StorageDAO;
import file.management.com.model.Storage;
import file.management.com.utils.ResponseAlert;
import org.bson.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "storage_child", urlPatterns = {"/storage/get_child"})
@MultipartConfig
public class StorageChildController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int parent = Integer.parseInt(req.getParameter("parent"));
        int offset = Integer.parseInt(req.getParameter("offset"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        StorageDAO storageDAO = new StorageDAO();
        List<Storage> storages = storageDAO.readChildItem(parent, offset, limit);
        ResponseAlert.getStringStorage(resp, storages);
    }
}
