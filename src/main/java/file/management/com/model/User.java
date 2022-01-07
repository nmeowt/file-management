package file.management.com.model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;

public class User {
    private ObjectId id;
    private String username;
    private String password;
    private String name;
    @BsonProperty(value = "created_at")
    private Date createdAt;
    @BsonProperty(value = "modified_at")
    private Date modifiedAt;
    @BsonProperty(value = "user_id")
    private int userId;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", username:'" + username + '\'' +
                ", name:'" + name + '\'' +
                ", createdAt:" + createdAt +
                ", modifiedAt:" + modifiedAt +
                ", userId:" + userId +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"username\":\"" + username + "\"" +
                ", \"name\":\"" + name + "\"" +
                ", \"createdAt\":\"" + createdAt + "\"" +
                ", \"modifiedAt\":\"" + modifiedAt + "\"" +
                ", \"userId\":" + userId +
                '}';
    }
}
