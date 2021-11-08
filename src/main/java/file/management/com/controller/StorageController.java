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

@WebServlet(name = "storage", urlPatterns = { "/storage" }, initParams = {
        @WebInitParam(name = "upload_path", value = "/var/www/upload") })
@MultipartConfig
public class StorageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int owner = Integer.parseInt(req.getParameter("owner"));
        int offset = Integer.parseInt(req.getParameter("offset"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        StorageDAO storageDAO = new StorageDAO();
        List<Storage> storages = storageDAO.read(owner, 0, offset, limit);
        ResponseAlert.getStringStorage(resp, storages);
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
