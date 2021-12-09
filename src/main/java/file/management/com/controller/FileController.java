package file.management.com.controller;

import file.management.com.dao.StorageDAO;
import file.management.com.model.Storage;
import file.management.com.utils.ResponseAlert;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "file", urlPatterns = {"/file"}, initParams = {
        @WebInitParam(name = "upload_path", value = "/var/www/upload")})
@MultipartConfig
public class FileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int type = 2;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Storage> storages = StorageController.getStorageByType(req, type);
        ResponseAlert.getStringStorages(resp, storages);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = null;
        boolean check = false;
        int owner = 1;
        String parent = req.getParameter("parent");
        String name = req.getParameter("name");
        String body = req.getParameter("body");
        String location = req.getParameter("location");
        int fileSize = Integer.valueOf(req.getParameter("file_size"));
        StorageDAO storageDAO = new StorageDAO();

        if (name == null) {
            message = "owner, type, name can not be empty";
        } else {
            Storage storage = new Storage();
            storage.setOwner(owner);
            storage.setType(type);
            storage.setParent(parent.equals("") ? 0 : Integer.parseInt(parent));
            storage.setName(name);
            storage.setBody(body);
            storage.setLocation(location);
            storage.setFileSize(fileSize);
            storage.setCreatedAt(new Date());
            storage.setModifiedAt(new Date());
            storageDAO.insert(storage);
            check = true;
            message = "inserted file successfully";
        }

        ResponseAlert.response(resp, message, check);
    }
}
