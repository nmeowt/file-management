package file.management.com.servlet;

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
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

@WebServlet(name = "upload-file", urlPatterns = {"/upload-file"}, initParams = {
        @WebInitParam(name = "upload_path", value = "/var/www/upload")})
@MultipartConfig
public class UploadFileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int parent = Integer.parseInt(req.getParameter("parent"));
        int owner = Direction.getOwner(req);
        String dir = null;
        String location = "";

        ServletConfig sc = getServletConfig();
        String path = sc.getInitParameter("upload_path");
        Part filePart = req.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        InputStream is = filePart.getInputStream();

        if (filePart.getSize() > 0) {
            ArrayList<String> listName = Direction.getDirection(parent);
            listName.add(fileName);
            for (String data : listName) {
                location += File.separator + data;
            }
            dir = path + File.separator + owner + location;
            Files.copy(is, Paths.get(dir), StandardCopyOption.REPLACE_EXISTING);
        }
        ResponseAlert.responseUploadedFile(resp, fileName, filePart.getSize(), File.separator + owner + location);
    }
}
