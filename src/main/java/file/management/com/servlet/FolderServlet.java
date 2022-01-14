package file.management.com.servlet;

import file.management.com.dao.StorageDAO;
import file.management.com.model.Storage;
import file.management.com.utils.Direction;
import file.management.com.utils.ResponseAlert;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "folder", urlPatterns = {"/folder"}, initParams = {
        @WebInitParam(name = "upload_path", value = "/var/www/upload")})
@MultipartConfig
public class FolderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int type = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Storage> storages = StorageServlet.getStorageByType(req, type);
        ResponseAlert.getStringStorages(resp, storages);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = null;
        boolean check = false;
        String dir = "";
        StorageDAO storageDAO = new StorageDAO();

        int owner = Direction.getOwner(req);
        int parent = Integer.parseInt(req.getParameter("parent"));
        String name = req.getParameter("name");

        if (name == null) {
            message = "name can not be empty";
        } else {
            ServletConfig sc = getServletConfig();
            String path = sc.getInitParameter("upload_path");
            String location = "";
            ArrayList<String> listName = Direction.getDirection(parent);
            listName.add(name);
            for (String data : listName) {
                location += File.separator + data;
            }
            dir = path + File.separator + owner + location;

            Storage storage = new Storage();
            storage.setOwner(owner);
            storage.setType(type);
            storage.setParent(parent);
            storage.setName(name);
            storage.setLocation(File.separator + owner + location);
            storage.setCreatedAt(new Date());
            storage.setModifiedAt(new Date());
            storageDAO.insert(storage);
            check = true;
            message = "create new folder successfully";
        }

        File theDir = new File(dir);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        ResponseAlert.response(resp, message, check);
    }

}
