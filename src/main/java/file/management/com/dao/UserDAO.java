package file.management.com.dao;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import file.management.com.constants.Constants;
import file.management.com.model.User;
import org.bson.Document;
import org.bson.types.ObjectId;

public class UserDAO {
    private static final String collection = "user";

    public boolean checkLogin( String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(Constants.CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Document> mongoCollection = database.getCollection(collection);
            Document user = mongoCollection.find(
                    Filters.and(
                            Filters.eq("username", username),
                            Filters.eq("password", password)
                    )).first();
            if (user != null) {
                return true;
            }
            return false;
        }
    }

    public void create(User user) {
        try (MongoClient mongoClient = MongoClients.create(Constants.CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Document> mongoCollection = database.getCollection(collection);
            Document document = new Document("_id", new ObjectId());
            document.append("username", user.getUsername())
                    .append("password", user.getPassword())
                    .append("name", user.getName())
                    .append("created_at", user.getCreatedAt())
                    .append("modified_at", user.getModifiedAt())
                    .append("user_id", user.getUserId());
            mongoCollection.insertOne(document);
        }
    }
}
