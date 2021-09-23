package file.management.com.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import file.management.com.constants.Constants;
import file.management.com.dao.StorageDAO;
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
    private static final String collection = "storage";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parent = req.getParameter("parent");
        StorageDAO storageDAO = new StorageDAO();
        List<Document> docs = storageDAO.readChildItem(Integer.parseInt(parent));
        StorageController.getString(resp, docs);
    }
}
