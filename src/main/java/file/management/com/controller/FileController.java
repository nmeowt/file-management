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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@WebServlet(name = "file", urlPatterns = {"/file"}, initParams = {
        @WebInitParam(name = "upload_path", value = "/var/www/upload")})
@MultipartConfig
public class FileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int type = 2;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = null;
        boolean check = false;
        int owner = 1;
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
            if (name == null) {
                message = "owner, type, name can not be empty";
            } else {
                Storage storage = new Storage();
                storage.setOwner(owner);
                storage.setType(type);
                storage.setParent(parent.equals("") ? null : Integer.parseInt(parent));
                storage.setName(name);
                storage.setBody("/upload" + File.separator + fileName);
                storage.setFileSize((int) filePart.getSize());
                storage.setCreatedAt(new Date());
                storage.setModifiedAt(new Date());
                storageDAO.insert(storage);
                check = true;
                message = "inserted file successfully";
            }
        } else {
            message = "file can not be empty";
        }

        ResponseAlert.response(resp, message, check);
    }
}
