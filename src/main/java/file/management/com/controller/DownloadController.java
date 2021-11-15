package file.management.com.controller;

import file.management.com.utils.ResponseAlert;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "download", urlPatterns = {"/download"})
@MultipartConfig
public class DownloadController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("file_name");
        FileInputStream fileInputStream = null;
        OutputStream responseOutputStream = null;
        String filePath = "/var/www/upload/" + fileName;
        File file = new File(filePath);

        String mimeType = req.getServletContext().getMimeType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        resp.setContentType(mimeType);
        resp.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        resp.setContentLength((int) file.length());

        fileInputStream = new FileInputStream(file);
        responseOutputStream = resp.getOutputStream();
        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
            responseOutputStream.write(bytes);
        }
        ResponseAlert.responseDownload(resp);
    }
}
