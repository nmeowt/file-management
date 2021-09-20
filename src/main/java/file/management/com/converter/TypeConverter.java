package file.management.com.converter;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import file.management.com.model.Type;
import org.bson.types.ObjectId;

public class TypeConverter {
    public static DBObject toDBObject(Type type) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append("name", type.getName());
        return builder.get();
    }

    public static Type toType(DBObject doc){
        Type type = new Type();
        ObjectId id = (ObjectId) doc.get("_id");
        type.setId(id.toString());
        type.setName((String) doc.get("name"));
        return type;
    }
}
