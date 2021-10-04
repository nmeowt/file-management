package file.management.com.utils;

import file.management.com.model.Storage;
import file.management.com.model.Type;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResponseAlert {
    public static void response(HttpServletResponse resp, String message, boolean check) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String context = "{" +
                "\"status\": \"" + check + "\"," +
                "\"message\": \"" + message + "\"," +
                "}";
        out.print(context);
        out.flush();
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    public static void getStringStorage(HttpServletResponse resp, List<Storage> storages) throws IOException {
        String context = "";
        for (Storage storage : storages) {
            context += storage.toJson() + ",";
        }
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + context + "]");
        out.flush();
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    public static void getStringType(HttpServletResponse resp, List<Type> types) throws IOException {
        String context = "";
        for (Type type : types) {
            context += type.toJson() + ",";
        }
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + context + "]");
        out.flush();
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private static void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    }
}
