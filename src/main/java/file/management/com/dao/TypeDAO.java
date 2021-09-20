package file.management.com.dao;

import com.mongodb.*;
import file.management.com.converter.TypeConverter;
import file.management.com.model.Type;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class TypeDAO {
    DBCollection col;

    public TypeDAO(MongoClient mongo) {
        this.col = mongo.getDB("file_management").getCollection("type");
    }

    public Type create(Type type) {
        DBObject doc = TypeConverter.toDBObject(type);
        this.col.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        type.setId(id.toString());
        return type;
    }

    public List<Type> getAll() {
        List<Type> types = new ArrayList<>();
        DBCursor cursor = col.find();
        while(cursor.hasNext()){
            DBObject doc = cursor.next();
            Type type = TypeConverter.toType(doc);
            types.add(type);
        }
        return types;
    }

    public Type getById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.col.findOne(query);
        return TypeConverter.toType(data);
    }

    public void update(Type type){
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(type.getId())).get();
        this.col.update(query, TypeConverter.toDBObject(type));
    }

    public void delete(Type type){
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(type.getId())).get();
        this.col.remove(query);
    }
}
