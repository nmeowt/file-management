package file.management.com.controller;

import com.mongodb.MongoClient;
import file.management.com.dao.StorageDAO;
import file.management.com.dao.TypeDAO;
import file.management.com.dao.UserDAO;
import file.management.com.dto.StorageDTO;
import file.management.com.model.Storage;
import file.management.com.model.Type;
import file.management.com.model.User;

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
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "storage", urlPatterns = {"/storage"}, initParams = {@WebInitParam(name = "upload_path", value = "/var/www/upload")})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class StorageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) req.getServletContext()
                .getAttribute("MONGO_CLIENT");
        StorageDAO storageDAO = new StorageDAO(mongo);
        TypeDAO typeDAO = new TypeDAO(mongo);
        UserDAO userDAO = new UserDAO(mongo);
        List<Storage> storages = storageDAO.getAll();
        List<StorageDTO> storagesDTO = new ArrayList<>();
        for (Storage storage : storages) {
            Type type = typeDAO.getById(storage.getType());
            User user = userDAO.getById(storage.getOwner());
            StorageDTO storageDTO = new StorageDTO(storage, type, user);
            storagesDTO.add(storageDTO);
        }
        String context = "";
        context = getString(context, storagesDTO);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + context + "]");
        out.flush();
    }

    public String getString(String context, List<StorageDTO> storagesDTO) {
        for (StorageDTO storageDTO : storagesDTO) {
            String data = "{" +
                    "\"id\": \"" + storageDTO.getId() + "\"," +
                    "\"owner\": {" +
                    "\"id\": \"" + storageDTO.getOwner().getId() + "\"," +
                    "\"name\": \"" + storageDTO.getOwner().getName() + "\"" +
                    "}" + "," +
                    "\"type\": {" +
                    "\"id\": \"" + storageDTO.getType().getId() + "\"," +
                    "\"name\": \"" + storageDTO.getType().getName() + "\"" +
                    "}" + "," +
                    "\"name\": \"" + storageDTO.getName() + "\"," +
                    "\"body\": \"" + storageDTO.getBody() + "\"," +
                    "\"created_at\": \"" + storageDTO.getCreatedAt() + "\"," +
                    "\"modified_at\": \"" + storageDTO.getModifiedAt() + "\"" +
                    "}";
            context += data;
        }
        return context;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, FileNotFoundException {
        String owner = req.getParameter("owner");
        String type = req.getParameter("type");
        String parent = req.getParameter("parent");
        String name = req.getParameter("name");

        MongoClient mongo = (MongoClient) req.getServletContext()
                .getAttribute("MONGO_CLIENT");
        StorageDAO storageDAO = new StorageDAO(mongo);

        ServletOutputStream os = resp.getOutputStream();
        ServletConfig sc = getServletConfig();
        String path = sc.getInitParameter("upload_path");

        String fileName = "";
        String dir = "";
        for (Part part : req.getParts()) {
            fileName = getFileName(part);
            if (fileName != "") {
                dir = path + File.separator + fileName;
//                part.write(dir);
            }
        }
        Storage storage = new Storage();
        storage.setOwner(owner);
        storage.setType(type);
        storage.setParent(parent);
        storage.setName(name);
        storage.setBody(dir);
        storage.setCreatedAt(Timestamp.from(Instant.now()));
        storage.setModifiedAt(Timestamp.from(Instant.now()));
        storageDAO.create(storage);

        req.setAttribute("message", "File " + fileName + " has uploaded successfully!");
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return "";
    }
}
