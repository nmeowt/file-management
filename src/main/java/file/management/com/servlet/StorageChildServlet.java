package file.management.com.servlet;

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
import java.util.List;

@WebServlet(name = "storage_child", urlPatterns = {"/storage/get_child"})
@MultipartConfig
public class StorageChildServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int owner = Integer.parseInt(req.getParameter("owner"));
        int parent = Integer.parseInt(req.getParameter("parent"));
        int offset = Integer.parseInt(req.getParameter("offset"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        StorageDAO storageDAO = new StorageDAO();
        List<Storage> storages = storageDAO.read(owner, parent, offset, limit);
        ResponseAlert.getStringStorages(resp, storages);
    }
}
