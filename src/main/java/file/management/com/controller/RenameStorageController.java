package file.management.com.controller;

import file.management.com.dao.StorageDAO;
import file.management.com.utils.ResponseAlert;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "rename_storage", urlPatterns = {"/storage/rename"})
@MultipartConfig
public class RenameStorageController extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = null;
        boolean check = false;
        int storageId = Integer.parseInt(req.getParameter("storage_id"));
        String name = req.getParameter("name");

        if(storageId == 0 || name == null){
            message = "storage id, name cannot be empty";
        } else {
            StorageDAO storageDAO = new StorageDAO();
            storageDAO.rename(storageId, name);
            check = true;
            message = "renamed successfully";
        }
        ResponseAlert.response(resp, message, check);
    }
}
