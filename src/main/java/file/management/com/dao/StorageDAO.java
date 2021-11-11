package file.management.com.dao;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.internal.async.SingleResultCallback;
import file.management.com.constants.Constants;
import file.management.com.model.Storage;
import file.management.com.mongo.Connection;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.*;


public class StorageDAO {
    private static final String collection = "storage";
    MongoClientSettings clientSettings;

    public StorageDAO() {
        this.clientSettings = Connection.open();
    }

    public List<Storage> read(int owner, int parent, int offset, int limit) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Storage> storages = database.getCollection(collection, Storage.class);
            List<Storage> storageList = storages.find(
                    and(
                            eq("owner", owner),
                            gte("storage_id", offset),
                            eq("parent", parent)
                    )
            ).sort(Sorts.descending("storage_id"))
                    .limit(limit).into(new ArrayList<>());
            return storageList;
        }
    }

    public List<Storage> readByType(int owner, int parent, int type ,int offset, int limit) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Storage> storages = database.getCollection(collection, Storage.class);
            List<Storage> storageList = storages.find(
                    and(
                            eq("type", type),
                            eq("owner", owner),
                            gte("storage_id", offset),
                            eq("parent", parent)
                    )
            ).sort(Sorts.descending("storage_id"))
                    .limit(limit).into(new ArrayList<>());
            return storageList;
        }
    }

    public List<Storage> readAllByType(int type) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Storage> storages = database.getCollection(collection, Storage.class);
            List<Storage> storageList = storages.find(
                    eq("type", type)
            ).into(new ArrayList<>());
            return storageList;
        }
    }

    public void insert(Storage storage) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Storage> storages = database.getCollection(collection, Storage.class);
            storages.insertOne(storage);
        }

    }

    public void rename(int storageId, String name) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Storage> storages = database.getCollection(collection, Storage.class);

            Storage storage = storages.find(eq("storage_id", storageId)).first();
            storage.setName(name);
            storage.setModifiedAt(new Date());
            Document filterByStorageId = new Document("storage_id", storage.getStorageId());
            storages.findOneAndReplace(filterByStorageId, storage);
        }
    }

    public void changeParent(int storageId, int parent) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Storage> storages = database.getCollection(collection, Storage.class);

            Storage storage = storages.find(eq("storage_id", storageId)).first();
            storage.setParent(parent);
            storage.setModifiedAt(new Date());
            Document filterByStorageId = new Document("storage_id", storage.getStorageId());
            storages.findOneAndReplace(filterByStorageId, storage);
        }
    }

    public void deleteById(int storageId) {
        try (MongoClient mongoClient = MongoClients.create(this.clientSettings)) {
            MongoDatabase database = mongoClient.getDatabase("file_management");
            MongoCollection<Storage> storages = database.getCollection(collection, Storage.class);
            List<Storage> storageList = storages.find(eq("parent", storageId))
                    .into(new ArrayList<>());
            if (storageList.size() != 0) {
                Document filterToDeleteParent = new Document("parent", storageId);
                storages.deleteMany(filterToDeleteParent);
            }
            Document filterDeleteStorage = new Document("storage_id", storageId);
            storages.deleteOne(filterDeleteStorage);
        }
    }
}
