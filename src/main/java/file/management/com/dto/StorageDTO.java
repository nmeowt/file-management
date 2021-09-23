package file.management.com.dto;

import file.management.com.model.Storage;
import file.management.com.model.Type;
import file.management.com.model.User;
import org.bson.types.ObjectId;

import java.sql.Timestamp;

public class StorageDTO {
    private ObjectId id;
    private Type type;
    private User owner;
    private int parent;
    private String name;
    private String body;
    private int storageId;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    public StorageDTO(Storage storage, Type type, User owner){
        this.id = storage.getId();
        this.type = type;
        this.owner = owner;
        this.parent = storage.getParent();
        this.name = storage.getName();
        this.body = storage.getBody();
        this.storageId = storage.getStorageId();
        this.createdAt = storage.getCreatedAt();
        this.modifiedAt = storage.getModifiedAt();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
