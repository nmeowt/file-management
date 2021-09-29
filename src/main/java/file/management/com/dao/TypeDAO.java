package file.management.com.dao;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import file.management.com.constants.Constants;
import file.management.com.model.Storage;
import file.management.com.model.Type;
import file.management.com.mongo.Connection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class TypeDAO {
    private static final String collection = "type";
    MongoClientSettings clientSettings;

    public TypeDAO() {
        this.clientSettings = Connection.open();
    }

    public void create(Type type) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Type> types = database.getCollection(collection, Type.class);
            types.insertOne(type);
        }
    }

    public List<Type> getAll() {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Type> types = database.getCollection(collection, Type.class);
            List<Type> typeList = types.find().into(new ArrayList<>());
            return typeList;
        }
    }

    public void update(Type type) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Type> types = database.getCollection(collection, Type.class);
            Bson filter = Filters.eq("type_id", type.getTypeId());
            Bson updateOperation = Updates.set("name", type.getName());
            types.updateOne(filter, updateOperation);
        }
    }

    public void delete(Type type) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Type> types = database.getCollection(collection, Type.class);
            StorageDAO storageDAO = new StorageDAO();
            List<Storage> storages = storageDAO.readAllByType(type.getTypeId());
            if (storages.size() != 0) {
                for (Storage storage: storages) {
                    storageDAO.deleteById(storage.getStorageId());
                }
            }
            Bson filter = Filters.eq("type_id", type.getTypeId());
            types.deleteOne(filter);
        }
    }
}
