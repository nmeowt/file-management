package file.management.com.converter;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import file.management.com.model.Storage;
import org.bson.types.ObjectId;

import java.sql.Timestamp;
import java.util.Date;

public class StorageConverter {
    public static DBObject toDBObject(Storage storage) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append("owner", new ObjectId(storage.getOwner()))
                .append("type", new ObjectId(storage.getType()))
                .append("parent", storage.getParent() == null ? null : new ObjectId(storage.getParent()))
                .append("name", storage.getName())
                .append("body", storage.getBody())
                .append("created_at", storage.getCreatedAt())
                .append("modified_at", storage.getModifiedAt());
        return builder.get();
    }

    public static Storage toStorage(DBObject doc) {
        Storage storage = new Storage();

        ObjectId id = (ObjectId) doc.get("_id");
        storage.setId(id.toString());

        ObjectId owner = (ObjectId) doc.get("owner");
        storage.setOwner(owner.toString());

        ObjectId type = (ObjectId) doc.get("type");
        storage.setType(type.toString());


        if(doc.get("parent") != null){
            ObjectId parent = (ObjectId) doc.get("parent");
            storage.setParent(parent.toString());
        } else {
            storage.setParent(null);
        }

        storage.setName((String) doc.get("name"));

        storage.setBody((String) doc.get("body"));

        Date create = (Date) doc.get("created_at");
        Timestamp createdAt = new Timestamp(create.getTime());
        storage.setCreatedAt(createdAt);

        Date modify = (Date) doc.get("modified_at");
        Timestamp modifiedAt = new Timestamp(modify.getTime());
        storage.setModifiedAt(modifiedAt);

        return storage;
    }
}
