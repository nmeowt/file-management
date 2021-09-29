package file.management.com.model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Type {
    private ObjectId id;
    private String name;
    @BsonProperty(value = "type_id")
    private int typeId;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"name\":\"" + name + "\"" +
                ", \"typeId\":" + typeId +
                '}';
    }
}
