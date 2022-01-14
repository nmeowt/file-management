package file.management.com.utils;

import file.management.com.dao.StorageDAO;
import file.management.com.model.Storage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class Direction {
    public static ArrayList<String> getDirection (int parent){
        StorageDAO storageDAO = new StorageDAO();
        ArrayList<String> listName = new ArrayList<>();
        while (parent > 0) {
            Storage parentStorage = storageDAO.readById(parent);
            listName.add(0, parentStorage.getName());
            parent = parentStorage.getParent();
        }
        return listName;
    }

    public static int getOwner(HttpServletRequest req){
        String token = (String) req.getAttribute("token");
        Jws<Claims> jws = JWT.parseJWT(token);
        return Integer.parseInt(jws.getBody().getSubject());
    }
}
