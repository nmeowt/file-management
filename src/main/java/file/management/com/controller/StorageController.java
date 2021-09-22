package file.management.com.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import file.management.com.constants.Constants;
import file.management.com.dao.StorageDAO;
import file.management.com.dao.TypeDAO;
import file.management.com.dao.UserDAO;
import file.management.com.dto.StorageDTO;
import file.management.com.model.Storage;
import file.management.com.model.Type;
import file.management.com.model.User;
import org.bson.Document;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "storage", urlPatterns = {"/storage"}, initParams = {@WebInitParam(name = "upload_path", value = "/var/www/upload")})
@MultipartConfig
public class StorageController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String collection = "storage";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String owner = req.getParameter("owner");

        try (MongoClient mongoClient = MongoClients.create(Constants.CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Document> mongoCollection = database.getCollection(collection);
            StorageDAO storageDAO = new StorageDAO();
            List<Document> docs = storageDAO.readRoot(mongoCollection, Integer.parseInt(owner));
            getString(resp, docs);
        }
    }

    static void getString(HttpServletResponse resp, List<Document> docs) throws IOException {
        String context = "";
        for (Document storage : docs) {
            context += storage.toJson() + ",";
        }
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + context + "]");
        out.flush();
    }

    static List<StorageDTO> convertDTO(TypeDAO typeDAO, UserDAO userDAO, List<Storage> storages) {
        List<StorageDTO> storagesDTO = new ArrayList<>();
        /*for (Storage storage : storages) {
            Type type = typeDAO.getById(storage.getType());
            User user = userDAO.getById(storage.getOwner());
            StorageDTO storageDTO = new StorageDTO(storage, type, user);
            storagesDTO.add(storageDTO);
        }*/
        return storagesDTO;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, FileNotFoundException {
        String message = null;
        boolean check = false;

        String owner = req.getParameter("owner");
        String type = req.getParameter("type");
        String parent = req.getParameter("parent");
        String name = req.getParameter("name");
        try (MongoClient mongoClient = MongoClients.create(Constants.CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Document> mongoCollection = database.getCollection(collection);
            StorageDAO storageDAO = new StorageDAO();
            String dir = null;

            ServletConfig sc = getServletConfig();
            String path = sc.getInitParameter("upload_path");
            Part filePart = req.getPart("body");
            String fileName = filePart.getSubmittedFileName();
            InputStream is = filePart.getInputStream();
            if (filePart.getSize() > 0) {
                dir = path + File.separator + fileName;
                Files.copy(is, Paths.get(dir), StandardCopyOption.REPLACE_EXISTING);
            }

            if (owner == null || type == null || name == null) {
                message = "owner, type, name can not be empty";
            } else {
                Storage storage = new Storage();
                storage.setOwner(Integer.parseInt(owner));
                storage.setType(Integer.parseInt(type));
                storage.setParent(parent.equals("") ? null : Integer.parseInt(parent));
                storage.setName(name);
                storage.setBody(dir);
                storage.setCreatedAt(Timestamp.from(Instant.now()));
                storage.setModifiedAt(Timestamp.from(Instant.now()));
                storageDAO.insert(mongoCollection, storage);
                check = true;
                message = "inserted storage successfully";
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
}
