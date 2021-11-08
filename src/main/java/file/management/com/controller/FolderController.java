package file.management.com.controller;

import file.management.com.dao.StorageDAO;
import file.management.com.model.Storage;
import file.management.com.utils.ResponseAlert;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "folder", urlPatterns = { "/folder" })
@MultipartConfig
public class FolderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int type = 1;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = null;
        boolean check = false;
        String parent = req.getParameter("parent");
        String name = req.getParameter("name");

        if (name == null) {
            message = "owner, type, name can not be empty";
        } else {
            StorageDAO storageDAO = new StorageDAO();
            Storage storage = new Storage();
            storage.setOwner(1);
            storage.setType(type);
            storage.setParent(parent.equals("") ? 0 : Integer.parseInt(parent));
            storage.setName(name);
            storage.setCreatedAt(new Date());
            storage.setModifiedAt(new Date());
            storageDAO.insert(storage);
            check = true;
            message = "create new folder successfully";
        }
        ResponseAlert.response(resp, message, check);
    }
}
