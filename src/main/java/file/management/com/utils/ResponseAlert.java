package file.management.com.utils;

import file.management.com.model.Storage;
import file.management.com.model.Type;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResponseAlert {
    public static void response(HttpServletResponse resp, String message, boolean check) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String context = "{" + "\"status\": \"" + check + "\"," + "\"message\": \"" + message + "\"" + "}";
        out.print(context);
        resp.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    public static void loginResponse(HttpServletResponse resp, String token) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String context = "{" + "\"token\": \"" + token + "\"" + "}";
        out.print(context);
        resp.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    public static void getStringStorages(HttpServletResponse resp, List<Storage> storages) throws IOException {
        String newContext = "";
        if (storages.toArray().length > 0) {
            String context = "";
            for (Storage storage : storages) {
                context += storage.toJson() + ",";
                newContext = context.substring(0, context.length() - 1);
            }
        }

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + newContext + "]");
        resp.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    public static void getStringStorage(HttpServletResponse resp, Storage storage) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(storage.toJson());
        resp.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    public static void getStringType(HttpServletResponse resp, List<Type> types) throws IOException {
        String context = "";
        for (Type type : types) {
            context += type.toJson() + ",";
        }
        String newContext = context.substring(0, context.length() - 1);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + newContext + "]");
        resp.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    private static void responseSet(HttpServletResponse resp, String context) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(context);
        resp.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    public static void responseUploadedFile(HttpServletResponse resp, String fileName, long fileSize, String location) throws IOException {
        String context = "{"
                + "\"file_name\": \"" + fileName
                + "\"," + "\"file_size\": \"" + fileSize
                + "\"," + "\"location\": \"" + location
                + "\"" + "}";
        responseSet(resp, context);
    }

    public static void responseDownloadFolder(HttpServletResponse resp, String zipFile) throws IOException {
        String context = "{"
                + "\"zip_file\": \"" + zipFile
                + "\"" + "}";
        responseSet(resp, context);
    }
}
