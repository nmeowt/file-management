package file.management.com.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import file.management.com.constants.Constants;
import file.management.com.model.Storage;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


public class StorageDAO {
    private static final String collection = "storage";

    public List<Document> readRoot(int owner) {
        try (MongoClient mongoClient = MongoClients.create(Constants.CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Document> mongoCollection = database.getCollection(collection);
            List<Document> storageList = mongoCollection.find(Filters.and(
                    Filters.eq("owner", owner),
                    Filters.eq("parent", null)
            )).into(new ArrayList<>());
            return storageList;
        }
    }

    public List<Document> readChildItem(int parent) {
        try (MongoClient mongoClient = MongoClients.create(Constants.CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Document> mongoCollection = database.getCollection(collection);
            List<Document> storageList = mongoCollection.find(
                    new Document("parent", parent)
            ).into(new ArrayList<>());
            return storageList;
        }
    }

    public void insert(Storage storage) {
        try (MongoClient mongoClient = MongoClients.create(Constants.CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Document> mongoCollection = database.getCollection(collection);
            Document document = new Document("_id", new ObjectId());
            document.append("owner", storage.getOwner())
                    .append("type", storage.getType())
                    .append("parent", storage.getParent())
                    .append("name", storage.getName())
                    .append("body", storage.getBody())
                    .append("created_at", storage.getCreatedAt())
                    .append("modified_at", storage.getModifiedAt());
            mongoCollection.insertOne(document);
        }
    }
}
