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
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    public static void getStringStorages(HttpServletResponse resp, List<Storage> storages) throws IOException {
        String context = "";
        for (Storage storage : storages) {
            context += storage.toJson() + ",";
        }
        String newContext = context.substring(0, context.length() - 1);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + newContext + "]");
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    public static void getStringStorage(HttpServletResponse resp, Storage storages) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(storages.toJson());
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    public static void getStringType(HttpServletResponse resp, List<Type> types) throws IOException {
        String context = "";
        for (Type type : types) {
            context += type.toJson() + ",";
        }
        String newContext = context.substring(0, context.length() - 1);
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + newContext + "]");
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    public static void reponseUploadedFile(HttpServletResponse resp, String fileName, long fileSize)throws IOException {
        String context = "{" + "\"file_name\": \"" + fileName + "\"," + "\"file_size\": \"" + fileSize + "\"" + "}";
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(context);
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    public static void responseDownload(HttpServletResponse resp){
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
    }

    private static void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
        resp.setHeader("Access-Control-Allow-Headers",
                "Accept,Authorization,Cache-Control,Content-Type,Keep-Alive,Origin,User-Agent,X-Requested-With");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
