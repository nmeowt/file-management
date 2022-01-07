package file.management.com.dao;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import file.management.com.constants.Constants;
import file.management.com.model.User;
import file.management.com.mongo.Connection;
import org.bson.Document;
import org.bson.types.ObjectId;

public class UserDAO {
    private static final String collection = "user";
    MongoClientSettings clientSettings;

    public UserDAO() {
        this.clientSettings = Connection.open();
    }

    public User checkLogin(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<User> users = database.getCollection(collection, User.class);
            User user = users.find(
                    Filters.and(
                            Filters.eq("username", username),
                            Filters.eq("password", password)
                    )).first();
            return user;
        }
    }

    public User getUserById(int userId) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<User> users = database.getCollection(collection, User.class);
            User user = users.find(Filters.eq("user_id", userId)).first();
            return user;
        }
    }

    public void create(User user) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<User> users = database.getCollection(collection, User.class);
            users.insertOne(user);
        }
    }

    public void update(User user){
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<User> users = database.getCollection(collection, User.class);
            User data = users.find(Filters.eq("user_id", user.getUserId())).first();
            data.setName(user.getName());
            data.setUsername(user.getUsername());
            data.setModifiedAt(user.getModifiedAt());

            Document filterByUserId = new Document("_id", data.getId());
            users.findOneAndReplace(filterByUserId,data);
        }
    }
}
