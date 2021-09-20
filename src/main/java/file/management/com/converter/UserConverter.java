package file.management.com.converter;

import com.mongodb.DBObject;
import file.management.com.model.User;
import org.bson.types.ObjectId;

import java.sql.Timestamp;
import java.util.Date;

public class UserConverter {

    public static User toUser(DBObject doc) {
        User user = new User();
        ObjectId id = (ObjectId) doc.get("_id");
        user.setId(id.toString());
        user.setUsername((String) doc.get("username"));
        user.setPassword((String) doc.get("password"));
        user.setName((String) doc.get("name"));
        Date createdAt = (Date) doc.get("created_at");
        user.setCreatedAt(new Timestamp(createdAt.getTime()));
        Date updatedAt = (Date) doc.get("updated_at");
        user.setUpdatedAt(new Timestamp(updatedAt.getTime()));
        return user;
    }
}
