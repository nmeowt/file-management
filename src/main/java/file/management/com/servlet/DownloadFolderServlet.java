package file.management.com.servlet;

import file.management.com.dao.StorageDAO;
import file.management.com.model.Storage;
import file.management.com.utils.ResponseAlert;
import file.management.com.utils.ZipFile;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

@WebServlet(name = "download_folder", urlPatterns = {"/download_folder"}, initParams = {
        @WebInitParam(name = "upload_path", value = "/var/www/upload")})
@MultipartConfig
public class DownloadFolderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        StorageDAO storageDAO = new StorageDAO();
        Storage storage = storageDAO.readById(id);
        ServletConfig sc = getServletConfig();
        String path = sc.getInitParameter("upload_path");

        final String sourceFile = path + storage.getLocation();
        final String zipFile = storage.getLocation() + ".zip";
        final FileOutputStream fos = new FileOutputStream(sourceFile + ".zip");
        final ZipOutputStream zipOut = new ZipOutputStream(fos);
        final File fileToZip = new File(sourceFile);

        ZipFile.zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();

        ResponseAlert.responseDownloadFolder(resp, zipFile);
    }
}
