package file.management.com.dao;

import com.mongodb.*;
import file.management.com.converter.TypeConverter;
import file.management.com.converter.UserConverter;
import file.management.com.model.Type;
import file.management.com.model.User;
import org.bson.types.ObjectId;

public class UserDAO {
    DBCollection col;

    public UserDAO(MongoClient mongo) {
        this.col = mongo.getDB("file_management").getCollection("user");
    }

    public boolean checkLogin(String username, String password) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("username", username)
                .append("password", password)
                .get();
        DBObject data = this.col.findOne(query);
        if (data != null){
            return true;
        }
        return false;
    }

    public User getById(String id){
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.col.findOne(query);
        return UserConverter.toUser(data);
    }

    public User create(User user) {
        DBObject doc = UserConverter.toDBObject(user);
        this.col.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        user.setId(id.toString());
        return user;
    }
}
