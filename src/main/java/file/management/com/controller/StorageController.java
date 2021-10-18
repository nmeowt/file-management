package file.management.com.controller;

import file.management.com.dao.StorageDAO;
import file.management.com.model.Storage;
import file.management.com.utils.ResponseAlert;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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
import java.util.Date;
import java.util.List;

@WebServlet(name = "storage", urlPatterns = {"/storage"}, initParams = {@WebInitParam(name = "upload_path", value = "/var/www/upload")})
@MultipartConfig
public class StorageController extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int owner = Integer.parseInt(req.getParameter("owner"));
        int offset = Integer.parseInt(req.getParameter("offset"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        StorageDAO storageDAO = new StorageDAO();
        List<Storage> storages = storageDAO.readRoot(owner, offset, limit);
        System.out.println("hello");
        ResponseAlert.getStringStorage(resp, storages);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, FileNotFoundException {
        String message = null;
        boolean check = false;

        String owner = req.getParameter("owner");
        String type = req.getParameter("type");
        String parent = req.getParameter("parent");
        String name = req.getParameter("name");
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
            storage.setCreatedAt(new Date());
            storage.setModifiedAt(new Date());
            storageDAO.insert(storage);
            check = true;
            message = "inserted storage successfully";
        }
        ResponseAlert.response(resp, message, check);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = null;
        boolean check = false;
        int storageId = Integer.parseInt(req.getParameter("storage_id"));
        if (storageId == 0) {
            message = "storage id cannot be empty";
        } else {
            StorageDAO storageDAO = new StorageDAO();
            storageDAO.deleteById(storageId);
            check = true;
            message = "deleted storage successfully";
        }
        ResponseAlert.response(resp, message, check);
    }
}
