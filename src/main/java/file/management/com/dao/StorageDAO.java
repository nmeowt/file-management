package file.management.com.dao;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import file.management.com.converter.StorageConverter;
import file.management.com.model.Storage;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class StorageDAO {
    DBCollection col;

    public StorageDAO(MongoClient mongo) {
        this.col = mongo.getDB("file_management").getCollection("storage");
    }

    public List<Storage> getAll() {
        List<Storage> storages = new ArrayList<Storage>();
        DBCursor cursor = col.find();
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Storage storage = StorageConverter.toStorage(doc);
            storages.add(storage);
        }
        return storages;
    }

    public Storage create(Storage storage){
        DBObject doc = StorageConverter.toDBObject(storage);
        this.col.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        storage.setId(id.toString());
        return storage;
    }
}