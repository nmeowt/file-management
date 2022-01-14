package file.management.com.servlet;

import file.management.com.dao.StorageDAO;
import file.management.com.utils.ResponseAlert;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "change_parent_storage", urlPatterns = {"/storage/change_parent"})
@MultipartConfig
public class ChangeParentServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = null;
        boolean check = false;
        int storageId = Integer.parseInt(req.getParameter("storage_id"));
        int parent = Integer.parseInt(req.getParameter("parent"));

        if(storageId == 0 || parent == 0){
            message = "storage id, parent cannot be empty";
        } else {
            StorageDAO storageDAO = new StorageDAO();
            storageDAO.changeParent(storageId, parent);
            check = true;
            message = "changed parent successfully";
        }
        ResponseAlert.response(resp, message, check);
    }
}
