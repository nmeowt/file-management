package file.management.com.servlet;

import file.management.com.dao.StorageDAO;
import file.management.com.model.Storage;
import file.management.com.utils.JWT;
import file.management.com.utils.ResponseAlert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet(name = "storage", urlPatterns = {"/storage"}, initParams = {
        @WebInitParam(name = "upload_path", value = "/var/www/upload")})
@MultipartConfig
public class StorageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        StorageDAO storageDAO = new StorageDAO();
        Storage storage = storageDAO.readById(id);
        ResponseAlert.getStringStorage(resp, storage);
    }

    static public List<Storage> getStorageByType(HttpServletRequest req, int type) {
        String token = (String) req.getAttribute("token");
        Jws<Claims> jws = JWT.parseJWT(token);
        int owner = Integer.parseInt(jws.getBody().getSubject());
        int parent = Integer.parseInt(req.getParameter("parent"));
        int offset = Integer.parseInt(req.getParameter("offset"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        StorageDAO storageDAO = new StorageDAO();
        List<Storage> storages = storageDAO.readByType(owner, parent, type, offset, limit);
        return storages;
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
