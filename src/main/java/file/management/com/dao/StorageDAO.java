package file.management.com.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import file.management.com.model.Storage;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


public class StorageDAO {
    public List<Document> readRoot(MongoCollection<Document> storageCollection, int owner) {
        List<Document> storageList = storageCollection.find(Filters.and(Filters.eq("owner", owner), Filters.eq("parent", null))).into(new ArrayList<>());
        return storageList;
    }

    public List<Document> readChildItem(MongoCollection<Document> storageCollection, int parent) {
        List<Document> storageList = storageCollection.find(new Document("parent", parent)).into(new ArrayList<>());
        return storageList;
    }

    public void insert(MongoCollection<Document> storageCollection, Storage storage) {
        Document document = new Document("_id", new ObjectId());
        document.append("owner", storage.getOwner())
                .append("type", storage.getType())
                .append("parent", storage.getParent())
                .append("name", storage.getName())
                .append("body", storage.getBody())
                .append("created_at", storage.getCreatedAt())
                .append("modified_at", storage.getModifiedAt());
        storageCollection.insertOne(document);
    }
}
