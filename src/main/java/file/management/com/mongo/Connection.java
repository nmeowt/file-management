package file.management.com.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import file.management.com.constants.Constants;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class Connection {
    public static MongoClientSettings open() {
        ConnectionString connectionString = new ConnectionString(Constants.CONNECTION_STRING);
        CodecRegistry pojoCodeRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodeRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
        return clientSettings;
    }
}
