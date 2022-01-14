package file.management.com.model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;

public class Storage {
    private int owner;
    private int type;
    private int parent;
    private String name;
    private String body;
    @BsonProperty(value = "file_size")
    private int fileSize;
    private String location;
    @BsonProperty(value = "created_at")
    private Date createdAt;
    @BsonProperty(value = "modified_at")
    private Date modifiedAt;
    @BsonProperty(value = "storage_id")
    private int storageId;

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + storageId +
                ", owner=" + owner +
                ", type=" + type +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", location='" + location + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"id\":\"" + storageId + "\"" +
                ", \"owner\":" + owner +
                ", \"type\":" + type +
                ", \"parent\":" + parent +
                ", \"name\":\"" + name + "\"" +
                ", \"body\":\"" + body + "\"" +
                ", \"location\":\"" + location + "\"" +
                ", \"fileSize\":\"" + fileSize + "\"" +
                ", \"createdAt\":\"" + createdAt + "\"" +
                ", \"modifiedAt\":\"" + modifiedAt + "\"" +
                '}';
    }
}
