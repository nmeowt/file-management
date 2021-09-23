package file.management.com.dao;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import file.management.com.constants.Constants;
import file.management.com.model.Type;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class TypeDAO {
    private static final String collection = "type";

    public void create(Type type) {
       try (MongoClient mongoClient = MongoClients.create(Constants.CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Document> mongoCollection = database.getCollection(collection);
            Document document = new Document("_id", new ObjectId());
            document.append("name", type.getName());
            mongoCollection.insertOne(document);
        }
    }

    public List<Document> getAll() {
        try (MongoClient mongoClient = MongoClients.create(Constants.CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Document> mongoCollection = database.getCollection(collection);
            List<Document> docs = mongoCollection.find().into(new ArrayList<>());
            return docs;
        }
    }

//    public Type getById(String id) {
//        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
//        DBObject data = this.col.findOne(query);
//    }

    public void update(Type type){
        try (MongoClient mongoClient = MongoClients.create(Constants.CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Document> mongoCollection = database.getCollection(collection);
            Bson filter = Filters.eq("type_id", type.getTypeId());
            Bson updateOperation = Updates.set("name", type.getName());
            mongoCollection.updateOne(filter, updateOperation);
        }
    }

    public void delete(Type type){
        try (MongoClient mongoClient = MongoClients.create(Constants.CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Document> mongoCollection = database.getCollection(collection);
            Bson filter = Filters.eq("type_id", type.getTypeId());
            mongoCollection.deleteOne(filter);
        }
    }
}
